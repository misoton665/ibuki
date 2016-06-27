package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

class TableRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  def findBySomething[A <: slick.driver.PostgresDriver.api.Table[R], R]
    (comp: (A) => slick.lifted.Rep[Boolean])
    (implicit tableQuery: slick.lifted.TableQuery[A]): Future[List[R]]
    = db.run(tableQuery.filter[slick.lifted.Rep[Boolean]](comp).to[List].result)
}
