package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import models.Tables.{Activity, ActivityRow}
import services.MessageHashGenerator
import services.DateConverter._

import scala.concurrent.Future

class ActivityRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends TableRepository(dbConfigProvider){

  import dbConfig.driver.api._

  implicit val Activities = TableQuery[Activity]

  private def findBy = findBySomething[Activity, ActivityRow] _

  private def create = insertSomething[Activity, ActivityRow](_.id) _

  def findById(id: Int): Future[List[ActivityRow]] = findBy(_.id === id)

  def findByActivityId(activityId: String): Future[List[ActivityRow]] = findBy(_.activityId === activityId)

  def findByUserId(userId: String): Future[List[ActivityRow]] = findBy(_.userId === userId)

  def findByGroupId(groupId: String): Future[List[ActivityRow]] = findBy(_.groupId === groupId)

  def createActivity(activityName: String, userId: String, groupId: String): Future[Int] = {
    val activityId = MessageHashGenerator.generateHash(activityName + groupId, userId)
    val date = generateNowDate
    val newActivity = ActivityRow(0, activityId, activityName, userId, groupId, date)

    create(newActivity)
  }
}
