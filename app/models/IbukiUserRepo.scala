package models

import javax.inject.Inject

import models.Tables.{IbukiUser, IbukiUserRow}
import play.api.db.slick.DatabaseConfigProvider
import services.DateConverter

class IbukiUserRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
  extends TableRepository[IbukiUser, IbukiUserRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val IbukiUsers = TableQuery[IbukiUser]

  def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByEmail(email: String) = findBy(_.email === email)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createIbukiUser(userId: String, userName: String, email: String) = {
    val date = DateConverter.generateNowDate
    val newIbukiUser = IbukiUserRow(0, userId, userName, email, date)

    create(newIbukiUser)
  }
}
