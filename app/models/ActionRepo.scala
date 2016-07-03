package models

import javax.inject.Inject

import models.Tables.{Action, ActionRow}
import play.api.db.slick.DatabaseConfigProvider
import services.{DateConverter, MessageHashGenerator}
import scala.concurrent.ExecutionContext.Implicits.global

class ActionRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider, ibukiUserRepo: IbukiUserRepo, ibukiGroupRepo: IbukiGroupRepo)
    extends TableRepository[Action, ActionRow](dbConfigProvider){

  import dbConfig.driver.api._

  implicit val Actions = TableQuery[Action]

  private def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByActionId(actionId: String) = findBy(_.actionId === actionId)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createAction(actionType: Int, actionBody: String, userId: String, groupId: String) = {
    val users = ibukiUserRepo.findByUserId(userId)
    val groups = ibukiGroupRepo.findByGroupId(groupId)
    val validation =
      for(
        user <- users;
        group <- groups
      ) yield {
        (user, group) match {
          case (List(), _) => false
          case (_, List()) => false
          case _ => true
        }
      }

    lazy val date = DateConverter.generateNowDate
    lazy val actionId = MessageHashGenerator.generateHash(actionBody + userId, date.toString)
    lazy val newAction = ActionRow(0, actionId, actionType, actionBody, userId, groupId, date)
    val createProcess = create(newAction)

    for(
      e <- validation;
      c <- createProcess
    ) yield {
      if (e) {
        Some(e)
      } else {
        None
      }
    }
  }
}
