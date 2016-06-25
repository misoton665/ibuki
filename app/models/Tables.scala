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
  lazy val schema: profile.SchemaDescription = Array(Action.schema, ActionTag.schema, Activity.schema, GroupUser.schema, IbukiGroup.schema, IbukiUser.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Action
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param actionId Database column action_id SqlType(text), Default(None)
   *  @param actionType Database column action_type SqlType(int4), Default(None)
   *  @param actionBody Database column action_body SqlType(text), Default(None)
   *  @param userId Database column user_id SqlType(text), Default(None)
   *  @param groupId Database column group_id SqlType(text), Default(None)
   *  @param date Database column date SqlType(date), Default(None) */
  case class ActionRow(id: Int, actionId: Option[String] = None, actionType: Option[Int] = None, actionBody: Option[String] = None, userId: Option[String] = None, groupId: Option[String] = None, date: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching ActionRow objects using plain SQL queries */
  implicit def GetResultActionRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Date]]): GR[ActionRow] = GR{
    prs => import prs._
    ActionRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[String], <<?[String], <<?[String], <<?[java.sql.Date]))
  }
  /** Table description of table action. Objects of this class serve as prototypes for rows in queries. */
  class Action(_tableTag: Tag) extends Table[ActionRow](_tableTag, "action") {
    def * = (id, actionId, actionType, actionBody, userId, groupId, date) <> (ActionRow.tupled, ActionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), actionId, actionType, actionBody, userId, groupId, date).shaped.<>({r=>import r._; _1.map(_=> ActionRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column action_id SqlType(text), Default(None) */
    val actionId: Rep[Option[String]] = column[Option[String]]("action_id", O.Default(None))
    /** Database column action_type SqlType(int4), Default(None) */
    val actionType: Rep[Option[Int]] = column[Option[Int]]("action_type", O.Default(None))
    /** Database column action_body SqlType(text), Default(None) */
    val actionBody: Rep[Option[String]] = column[Option[String]]("action_body", O.Default(None))
    /** Database column user_id SqlType(text), Default(None) */
    val userId: Rep[Option[String]] = column[Option[String]]("user_id", O.Default(None))
    /** Database column group_id SqlType(text), Default(None) */
    val groupId: Rep[Option[String]] = column[Option[String]]("group_id", O.Default(None))
    /** Database column date SqlType(date), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
  }
  /** Collection-like TableQuery object for table Action */
  lazy val Action = new TableQuery(tag => new Action(tag))

  /** Entity class storing rows of table ActionTag
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param actionId Database column action_id SqlType(text), Default(None)
   *  @param tagName Database column tag_name SqlType(text), Default(None) */
  case class ActionTagRow(id: Int, actionId: Option[String] = None, tagName: Option[String] = None)
  /** GetResult implicit for fetching ActionTagRow objects using plain SQL queries */
  implicit def GetResultActionTagRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[ActionTagRow] = GR{
    prs => import prs._
    ActionTagRow.tupled((<<[Int], <<?[String], <<?[String]))
  }
  /** Table description of table action_tag. Objects of this class serve as prototypes for rows in queries. */
  class ActionTag(_tableTag: Tag) extends Table[ActionTagRow](_tableTag, "action_tag") {
    def * = (id, actionId, tagName) <> (ActionTagRow.tupled, ActionTagRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), actionId, tagName).shaped.<>({r=>import r._; _1.map(_=> ActionTagRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column action_id SqlType(text), Default(None) */
    val actionId: Rep[Option[String]] = column[Option[String]]("action_id", O.Default(None))
    /** Database column tag_name SqlType(text), Default(None) */
    val tagName: Rep[Option[String]] = column[Option[String]]("tag_name", O.Default(None))
  }
  /** Collection-like TableQuery object for table ActionTag */
  lazy val ActionTag = new TableQuery(tag => new ActionTag(tag))

  /** Entity class storing rows of table Activity
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param activityId Database column activity_id SqlType(text), Default(None)
   *  @param activityName Database column activity_name SqlType(text), Default(None)
   *  @param userId Database column user_id SqlType(text), Default(None)
   *  @param groupId Database column group_id SqlType(text), Default(None)
   *  @param date Database column date SqlType(date), Default(None) */
  case class ActivityRow(id: Int, activityId: Option[String] = None, activityName: Option[String] = None, userId: Option[String] = None, groupId: Option[String] = None, date: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching ActivityRow objects using plain SQL queries */
  implicit def GetResultActivityRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Date]]): GR[ActivityRow] = GR{
    prs => import prs._
    ActivityRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[String], <<?[java.sql.Date]))
  }
  /** Table description of table activity. Objects of this class serve as prototypes for rows in queries. */
  class Activity(_tableTag: Tag) extends Table[ActivityRow](_tableTag, "activity") {
    def * = (id, activityId, activityName, userId, groupId, date) <> (ActivityRow.tupled, ActivityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), activityId, activityName, userId, groupId, date).shaped.<>({r=>import r._; _1.map(_=> ActivityRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column activity_id SqlType(text), Default(None) */
    val activityId: Rep[Option[String]] = column[Option[String]]("activity_id", O.Default(None))
    /** Database column activity_name SqlType(text), Default(None) */
    val activityName: Rep[Option[String]] = column[Option[String]]("activity_name", O.Default(None))
    /** Database column user_id SqlType(text), Default(None) */
    val userId: Rep[Option[String]] = column[Option[String]]("user_id", O.Default(None))
    /** Database column group_id SqlType(text), Default(None) */
    val groupId: Rep[Option[String]] = column[Option[String]]("group_id", O.Default(None))
    /** Database column date SqlType(date), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
  }
  /** Collection-like TableQuery object for table Activity */
  lazy val Activity = new TableQuery(tag => new Activity(tag))

  /** Entity class storing rows of table GroupUser
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param groupId Database column group_id SqlType(text), Default(None)
   *  @param userId Database column user_id SqlType(text), Default(None)
   *  @param date Database column date SqlType(date), Default(None) */
  case class GroupUserRow(id: Int, groupId: Option[String] = None, userId: Option[String] = None, date: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching GroupUserRow objects using plain SQL queries */
  implicit def GetResultGroupUserRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Date]]): GR[GroupUserRow] = GR{
    prs => import prs._
    GroupUserRow.tupled((<<[Int], <<?[String], <<?[String], <<?[java.sql.Date]))
  }
  /** Table description of table group_user. Objects of this class serve as prototypes for rows in queries. */
  class GroupUser(_tableTag: Tag) extends Table[GroupUserRow](_tableTag, "group_user") {
    def * = (id, groupId, userId, date) <> (GroupUserRow.tupled, GroupUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), groupId, userId, date).shaped.<>({r=>import r._; _1.map(_=> GroupUserRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column group_id SqlType(text), Default(None) */
    val groupId: Rep[Option[String]] = column[Option[String]]("group_id", O.Default(None))
    /** Database column user_id SqlType(text), Default(None) */
    val userId: Rep[Option[String]] = column[Option[String]]("user_id", O.Default(None))
    /** Database column date SqlType(date), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
  }
  /** Collection-like TableQuery object for table GroupUser */
  lazy val GroupUser = new TableQuery(tag => new GroupUser(tag))

  /** Entity class storing rows of table IbukiGroup
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param groupId Database column group_id SqlType(text), Default(None)
   *  @param groupName Database column group_name SqlType(text), Default(None)
   *  @param ownerId Database column owner_id SqlType(text), Default(None)
   *  @param date Database column date SqlType(date), Default(None) */
  case class IbukiGroupRow(id: Int, groupId: Option[String] = None, groupName: Option[String] = None, ownerId: Option[String] = None, date: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching IbukiGroupRow objects using plain SQL queries */
  implicit def GetResultIbukiGroupRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Date]]): GR[IbukiGroupRow] = GR{
    prs => import prs._
    IbukiGroupRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[java.sql.Date]))
  }
  /** Table description of table ibuki_group. Objects of this class serve as prototypes for rows in queries. */
  class IbukiGroup(_tableTag: Tag) extends Table[IbukiGroupRow](_tableTag, "ibuki_group") {
    def * = (id, groupId, groupName, ownerId, date) <> (IbukiGroupRow.tupled, IbukiGroupRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), groupId, groupName, ownerId, date).shaped.<>({r=>import r._; _1.map(_=> IbukiGroupRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column group_id SqlType(text), Default(None) */
    val groupId: Rep[Option[String]] = column[Option[String]]("group_id", O.Default(None))
    /** Database column group_name SqlType(text), Default(None) */
    val groupName: Rep[Option[String]] = column[Option[String]]("group_name", O.Default(None))
    /** Database column owner_id SqlType(text), Default(None) */
    val ownerId: Rep[Option[String]] = column[Option[String]]("owner_id", O.Default(None))
    /** Database column date SqlType(date), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
  }
  /** Collection-like TableQuery object for table IbukiGroup */
  lazy val IbukiGroup = new TableQuery(tag => new IbukiGroup(tag))

  /** Entity class storing rows of table IbukiUser
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(text), Default(None)
   *  @param userName Database column user_name SqlType(text), Default(None)
   *  @param email Database column email SqlType(text), Default(None)
   *  @param date Database column date SqlType(date), Default(None) */
  case class IbukiUserRow(id: Int, userId: Option[String] = None, userName: Option[String] = None, email: Option[String] = None, date: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching IbukiUserRow objects using plain SQL queries */
  implicit def GetResultIbukiUserRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Date]]): GR[IbukiUserRow] = GR{
    prs => import prs._
    IbukiUserRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[java.sql.Date]))
  }
  /** Table description of table ibuki_user. Objects of this class serve as prototypes for rows in queries. */
  class IbukiUser(_tableTag: Tag) extends Table[IbukiUserRow](_tableTag, "ibuki_user") {
    def * = (id, userId, userName, email, date) <> (IbukiUserRow.tupled, IbukiUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), userId, userName, email, date).shaped.<>({r=>import r._; _1.map(_=> IbukiUserRow.tupled((_1.get, _2, _3, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(text), Default(None) */
    val userId: Rep[Option[String]] = column[Option[String]]("user_id", O.Default(None))
    /** Database column user_name SqlType(text), Default(None) */
    val userName: Rep[Option[String]] = column[Option[String]]("user_name", O.Default(None))
    /** Database column email SqlType(text), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Default(None))
    /** Database column date SqlType(date), Default(None) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("date", O.Default(None))
  }
  /** Collection-like TableQuery object for table IbukiUser */
  lazy val IbukiUser = new TableQuery(tag => new IbukiUser(tag))
}
