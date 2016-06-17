package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Poe.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Poe
   *  @param id Database column id_ SqlType(int4), Default(None)
   *  @param name Database column name SqlType(bpchar), Length(16,false), Default(None)
   *  @param age Database column age SqlType(int4), Default(None) */
  case class PoeRow(id: Option[Int] = None, name: Option[String] = None, age: Option[Int] = None)
  /** GetResult implicit for fetching PoeRow objects using plain SQL queries */
  implicit def GetResultPoeRow(implicit e0: GR[Option[Int]], e1: GR[Option[String]]): GR[PoeRow] = GR{
    prs => import prs._
    PoeRow.tupled((<<?[Int], <<?[String], <<?[Int]))
  }
  /** Table description of table poe. Objects of this class serve as prototypes for rows in queries. */
  class Poe(_tableTag: Tag) extends Table[PoeRow](_tableTag, "poe") {
    def * = (id, name, age) <> (PoeRow.tupled, PoeRow.unapply)

    /** Database column id_ SqlType(int4), Default(None) */
    val id: Rep[Option[Int]] = column[Option[Int]]("id_", O.Default(None))
    /** Database column name SqlType(bpchar), Length(16,false), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(16,varying=false), O.Default(None))
    /** Database column age SqlType(int4), Default(None) */
    val age: Rep[Option[Int]] = column[Option[Int]]("age", O.Default(None))
  }
  /** Collection-like TableQuery object for table Poe */
  lazy val Poe = new TableQuery(tag => new Poe(tag))
}
