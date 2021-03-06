package models

import javax.inject.Inject

import models.Tables.{GroupUser, GroupUserRow, IbukiGroupRow, IbukiUserRow}
import play.api.db.slick.DatabaseConfigProvider
import services.{DateConverter, TableRepositoryMessages}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GroupUserRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider, groupRepo: IbukiGroupRepo, userRepo: IbukiUserRepo)
  extends TableRepository[GroupUser, GroupUserRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val Actions = TableQuery[GroupUser]

  private val insertWithId = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByUserId(userId: String) = findBy(_.userId === userId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def insertGroupUser(groupId: String, userId: String) = {
    val groups: Future[List[IbukiGroupRow]] = groupRepo.findByGroupId(groupId)
    val users: Future[List[IbukiUserRow]] = userRepo.findByUserId(userId)
    val groupUsers: Future[List[GroupUserRow]] = this.findByUserId(userId)
    val validation: Future[Boolean] =
      for (
        group <- groups;
        user <- users;
        groupUser <- groupUsers
      ) yield {
        (group, user, groupUser) match {
          case (List(), _, _) => false
          case (_, List(), _) => false
          case _ if !groupUser.exists(_.groupId == groupId) => true
          case _ => false
        }
      }

    val date = DateConverter.generateNowDate
    val newGroupUser = GroupUserRow(0, groupId, userId, date)

    validation.flatMap{
      if (_)
        insertWithId(newGroupUser).map(Right(_))
      else
        Future(Left(TableRepositoryMessages.ValidationErrorMessage))
    }
  }
}
