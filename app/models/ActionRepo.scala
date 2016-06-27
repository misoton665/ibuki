package models

import javax.inject.Inject

import models.Tables.{Action, ActionRow}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

class ActionRepo @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends TableRepository(dbConfigProvider){

  import dbConfig.driver.api._

  implicit val Actions: TableQuery[Action] = TableQuery[Action]

  def findBy[A <: slick.lifted.Rep[Action]] = findBySomething[A, Action, ActionRow] _

  def findByGroupId(groupId: String): Future[List[ActionRow]] = findBy(_.groupId === groupId)

  def findById(id: Int): Future[List[ActionRow]] = findBy(_.id === id)

  def findByActionId(actionId: String): Future[List[ActionRow]] = findBy(_.actionId === actionId)

  def findByUserId(userId: String): Future[List[ActionRow]] = findBy(_.userId === userId)
}
