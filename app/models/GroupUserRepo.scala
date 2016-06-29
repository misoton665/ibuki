package models

import javax.inject.Inject

import models.Tables.{GroupUser, GroupUserRow}
import play.api.db.slick.DatabaseConfigProvider
import services.DateConverter

class GroupUserRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
  extends TableRepository[GroupUser, GroupUserRow](dbConfigProvider){

  import dbConfig.driver.api._

  implicit val Actions = TableQuery[GroupUser]

  private def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createGroupUser(groupId: String, userId: String) = {
    val date = DateConverter.generateNowDate
    val newGroupUser = GroupUserRow(0, groupId, userId, date)

    create(newGroupUser)
  }
}
