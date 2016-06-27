package models

import javax.inject.Inject

import models.Tables.{IbukiGroup, IbukiGroupRow}
import play.api.db.slick.DatabaseConfigProvider
import services.DateConverter

class IbukiGroupRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
  extends TableRepository[IbukiGroup, IbukiGroupRow](dbConfigProvider) {

  import dbConfig.driver.api._

  implicit val IbukiGroups = TableQuery[IbukiGroup]

  def create = insert(_.id) _

  def findById(id: Int) = findBy(_.id === id)

  def findByGroupId(groupId: String) = findBy(_.groupId === groupId)

  def findByOwnerId(ownerId: String) = findBy(_.ownerId === ownerId)

  def findByDate(date: java.sql.Date) = findBy(_.date === date)

  def createIbukiGroup(groupId: String, groupName: String, ownerId: String) = {
    // TODO: validate the parameters

    val date = DateConverter.generateNowDate
    val newIbukiGroup = IbukiGroupRow(0, groupId, groupName, ownerId, date)

    create(newIbukiGroup)
  }
}
