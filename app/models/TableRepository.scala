package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * The Super class for use function which control DB on specific table TT
  *
  * @param dbConfigProvider for api driver
  * @tparam TT TableType of DB in models/Tables.scala, be structured by RT
  * @tparam RT RowType of DB in models/Tables.scala, structure for TT
  */
class TableRepository[TT <: slick.driver.PostgresDriver.api.Table[RT], RT] @Inject()
    (protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  /**
    * The control function for find some rows.
    *
    * @param comp A compare function for row specifying, for instance, _.id === id
    * @param tableQuery TableQuery, for instance, implicit val Activities = TableQuery[Activity], on Activity = TT
    * @return
    */
  def findBy
    (comp: (TT) => slick.lifted.Rep[Boolean])
    (implicit tableQuery: slick.lifted.TableQuery[TT]): Future[List[RT]]
    = db.run(tableQuery.filter[slick.lifted.Rep[Boolean]](comp).to[List].result)

  /**
    * The control function for create a new row into the table.
    *
    * @param idExtractor An ID extractor, for instance, _.id
    * @param row A new row which will insert into TT Table.
    * @param tableQuery TableQuery, for instance, implicit val Activities = TableQuery[Activity], on Activity = TT
    * @return An ID of a new row.
    */
  def insert
    (idExtractor: (TT) => slick.lifted.Rep[Int])(row: RT)
    (implicit tableQuery: slick.lifted.TableQuery[TT]): Future[Int]
    = db.run(tableQuery returning tableQuery.map(idExtractor) += row)
}
