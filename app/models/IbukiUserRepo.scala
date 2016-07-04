package models

import javax.inject.Inject

import models.Tables.{IbukiUser, IbukiUserRow}
import play.api.db.slick.DatabaseConfigProvider
import services.DateConverter
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IbukiUserRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
  extends TableRepository[IbukiUser, IbukiUserRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val IbukiUsers = TableQuery[IbukiUser]

  def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByEmail(email: String) = findBy(_.email === email)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createIbukiUser(userId: String, userName: String, email: String): Future[Option[Int]] = {
    val validation = true //email.matches("""/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/""")
    lazy val date = DateConverter.generateNowDate
    lazy val newIbukiUser = IbukiUserRow(0, userId, userName, email, date)

    if (validation) {
      create(newIbukiUser).map(Some(_))
    } else {
      Future(None)
    }
  }
}
