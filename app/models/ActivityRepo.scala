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

  def findById(id: Int): Future[ActivityRow] =
    db.run(Activities.filter(_.id === id).result.head)

}
