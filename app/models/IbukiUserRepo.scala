package models

import javax.inject.Inject

import models.Tables.{IbukiUser, IbukiUserRow}
import play.api.db.slick.DatabaseConfigProvider
import services.{DateConverter, TableRepositoryMessages}
import services.TableRepositoryMessages.TableRepositoryMessage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IbukiUserRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
  extends TableRepository[IbukiUser, IbukiUserRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val IbukiUsers = TableQuery[IbukiUser]

  private val insertWithId = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByEmail(email: String) = findBy(_.email === email)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def insertIbukiUser(userId: String, userName: String, email: String): Future[Either[TableRepositoryMessage, Int]] = {
    val users = this.findByUserId(userId)
    def isValidEmail(email: String): Boolean =
      """(\w+)@([\w\.]+)""".r.unapplySeq(email).isDefined
    val validation =
      for (user <- users) yield {
        (user match {
          case List() => true
          case _ => false
        }) && isValidEmail(email)
      }

    lazy val date = DateConverter.generateNowDate
    lazy val newIbukiUser = IbukiUserRow(0, userId, userName, email, date)

    validation.flatMap{
      if (_)
        insertWithId(newIbukiUser).map(Right(_))
      else
        Future(Left(TableRepositoryMessages.ValidationErrorMessage))
    }
  }
}
