package models

import javax.inject.Inject

import models.Tables.{Action, ActionRow}
import play.api.db.slick.DatabaseConfigProvider
import services.{DateConverter, MessageHashGenerator}

class ActionRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
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
    // TODO: validate the parameters

    val date = DateConverter.generateNowDate
    val actionId = MessageHashGenerator.generateHash(actionBody + userId, date.toString)
    val newAction = ActionRow(0, actionId, actionType, actionBody, userId, groupId, date)

    create(newAction)
  }
}
