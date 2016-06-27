package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

class TableRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  type TableType[RT] = slick.driver.PostgresDriver.api.Table[RT]

  def findBySomething[TT <: TableType[RT], RT]
    (comp: (TT) => slick.lifted.Rep[Boolean])
    (implicit tableQuery: slick.lifted.TableQuery[TT]): Future[List[RT]]
    = db.run(tableQuery.filter[slick.lifted.Rep[Boolean]](comp).to[List].result)

  def insertSomething[TT <: TableType[RT], RT]
    (idExtractor: (TT) => slick.lifted.Rep[Int])(row: RT)
    (implicit tableQuery: slick.lifted.TableQuery[TT]): Future[Int]
    = db.run(tableQuery returning tableQuery.map(idExtractor) += row)
}
