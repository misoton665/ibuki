package models

import javax.inject.Inject

import models.Tables.{IbukiGroup, IbukiGroupRow}
import play.api.db.slick.DatabaseConfigProvider
import services.DateConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IbukiGroupRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider, ibukiUserRepo: IbukiUserRepo)
  extends TableRepository[IbukiGroup, IbukiGroupRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val IbukiGroups = TableQuery[IbukiGroup]

  private val insertWithId = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByOwnerId(ownerId: String) = findBy(_.ownerId === ownerId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def insertIbukiGroup(groupId: String, groupName: String, ownerId: String): Future[Option[Int]] = {
    // validation
    val groups = this.findByGroupId(groupId)
    val owners = ibukiUserRepo.findByUserId(ownerId)
    val validation =
      for (
        group <- groups;
        owner <- owners
      ) yield {
        (group, owner) match {
          case (_, List()) => false
          case _ if group.isEmpty => true
          case _ => false
        }
      }

    // create a new group
    lazy val date = DateConverter.generateNowDate
    lazy val newGroup = IbukiGroupRow(0, groupId, groupName, ownerId, date)

    validation.flatMap(if (_) insertWithId(newGroup).map(Some(_)) else Future(None))
  }
}
