package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import models.Tables.{Activity, ActivityRow}

import scala.concurrent.Future

class ActivityRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._

  private val Activities = TableQuery[Activity]

  def findById(id: Int): Future[List[ActivityRow]] =
    db.run(Activities.filter(_.id === id).to[List].result)

  def findByActivityId(activityId: String): Future[List[ActivityRow]] =
    db.run(Activities.filter(_.activityId === activityId).to[List].result)

  def findByUserId(userId: String): Future[List[ActivityRow]] =
    db.run(Activities.filter(_.userId === userId).to[List].result)

  def findByGroupId(groupId: String): Future[List[ActivityRow]] =
    db.run(Activities.filter(_.groupId === groupId).to[List].result)

}
