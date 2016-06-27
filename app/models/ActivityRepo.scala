package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import models.Tables.{Activity, ActivityRow}
import services.{DateConverter, MessageHashGenerator}

class ActivityRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
    extends TableRepository[Activity, ActivityRow](dbConfigProvider){

  import dbConfig.driver.api._

  implicit val Activities = TableQuery[Activity]

  private def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByActivityId(activityId: String) = findBy(_.activityId === activityId)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createActivity(activityName: String, userId: String, groupId: String) = {
    // TODO: validate the parameters

    val activityId = MessageHashGenerator.generateHash(activityName + groupId, userId)
    val date = DateConverter.generateNowDate
    val newActivity = ActivityRow(0, activityId, activityName, userId, groupId, date)

    create(newActivity)
  }
}
