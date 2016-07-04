package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import models.Tables.{Activity, ActivityRow}
import services.{DateConverter, MessageHashGenerator}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ActivityRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider, ibukiUserRepo: IbukiUserRepo, ibukiGroupRepo: IbukiGroupRepo)
  extends TableRepository[Activity, ActivityRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val Activities = TableQuery[Activity]

  private def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByActivityId(activityId: String) = findBy(_.activityId === activityId)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createActivity(activityName: String, userId: String, groupId: String) = {
    val users = ibukiUserRepo.findByUserId(userId)
    val groups = ibukiGroupRepo.findByGroupId(groupId)
    val validation =
      for (
        user <- users;
        group <- groups
      ) yield {
        (user, group) match {
          case (List(), _) => false
          case (_, List()) => false
          case _ => true
        }
      }

    lazy val activityId = MessageHashGenerator.generateHash(activityName + groupId, userId)
    lazy val date = DateConverter.generateNowDate
    lazy val newActivity = ActivityRow(0, activityId, activityName, userId, groupId, date)

    validation.flatMap(if (_) create(newActivity).map(Some(_)) else Future(None))
  }
}
