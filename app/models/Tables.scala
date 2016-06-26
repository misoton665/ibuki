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
   *  @param actionId Database column action_id SqlType(text)
   *  @param actionType Database column action_type SqlType(int4)
   *  @param actionBody Database column action_body SqlType(text)
   *  @param userId Database column user_id SqlType(text)
   *  @param groupId Database column group_id SqlType(text)
   *  @param date Database column date SqlType(date) */
  case class ActionRow(id: Int, actionId: String, actionType: Int, actionBody: String, userId: String, groupId: String, date: java.sql.Date)
  /** GetResult implicit for fetching ActionRow objects using plain SQL queries */
  implicit def GetResultActionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[ActionRow] = GR{
    prs => import prs._
    ActionRow.tupled((<<[Int], <<[String], <<[Int], <<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table action. Objects of this class serve as prototypes for rows in queries. */
  class Action(_tableTag: Tag) extends Table[ActionRow](_tableTag, "action") {
    def * = (id, actionId, actionType, actionBody, userId, groupId, date) <> (ActionRow.tupled, ActionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(actionId), Rep.Some(actionType), Rep.Some(actionBody), Rep.Some(userId), Rep.Some(groupId), Rep.Some(date)).shaped.<>({r=>import r._; _1.map(_=> ActionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column action_id SqlType(text) */
    val actionId: Rep[String] = column[String]("action_id")
    /** Database column action_type SqlType(int4) */
    val actionType: Rep[Int] = column[Int]("action_type")
    /** Database column action_body SqlType(text) */
    val actionBody: Rep[String] = column[String]("action_body")
    /** Database column user_id SqlType(text) */
    val userId: Rep[String] = column[String]("user_id")
    /** Database column group_id SqlType(text) */
    val groupId: Rep[String] = column[String]("group_id")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
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
   *  @param activityId Database column activity_id SqlType(text)
   *  @param activityName Database column activity_name SqlType(text)
   *  @param userId Database column user_id SqlType(text)
   *  @param groupId Database column group_id SqlType(text)
   *  @param date Database column date SqlType(date) */
  case class ActivityRow(id: Int, activityId: String, activityName: String, userId: String, groupId: String, date: java.sql.Date)
  /** GetResult implicit for fetching ActivityRow objects using plain SQL queries */
  implicit def GetResultActivityRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[ActivityRow] = GR{
    prs => import prs._
    ActivityRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table activity. Objects of this class serve as prototypes for rows in queries. */
  class Activity(_tableTag: Tag) extends Table[ActivityRow](_tableTag, "activity") {
    def * = (id, activityId, activityName, userId, groupId, date) <> (ActivityRow.tupled, ActivityRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(activityId), Rep.Some(activityName), Rep.Some(userId), Rep.Some(groupId), Rep.Some(date)).shaped.<>({r=>import r._; _1.map(_=> ActivityRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column activity_id SqlType(text) */
    val activityId: Rep[String] = column[String]("activity_id")
    /** Database column activity_name SqlType(text) */
    val activityName: Rep[String] = column[String]("activity_name")
    /** Database column user_id SqlType(text) */
    val userId: Rep[String] = column[String]("user_id")
    /** Database column group_id SqlType(text) */
    val groupId: Rep[String] = column[String]("group_id")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
  }
  /** Collection-like TableQuery object for table Activity */
  lazy val Activity = new TableQuery(tag => new Activity(tag))

  /** Entity class storing rows of table GroupUser
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param groupId Database column group_id SqlType(text)
   *  @param userId Database column user_id SqlType(text)
   *  @param date Database column date SqlType(date) */
  case class GroupUserRow(id: Int, groupId: String, userId: String, date: java.sql.Date)
  /** GetResult implicit for fetching GroupUserRow objects using plain SQL queries */
  implicit def GetResultGroupUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[GroupUserRow] = GR{
    prs => import prs._
    GroupUserRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table group_user. Objects of this class serve as prototypes for rows in queries. */
  class GroupUser(_tableTag: Tag) extends Table[GroupUserRow](_tableTag, "group_user") {
    def * = (id, groupId, userId, date) <> (GroupUserRow.tupled, GroupUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(groupId), Rep.Some(userId), Rep.Some(date)).shaped.<>({r=>import r._; _1.map(_=> GroupUserRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column group_id SqlType(text) */
    val groupId: Rep[String] = column[String]("group_id")
    /** Database column user_id SqlType(text) */
    val userId: Rep[String] = column[String]("user_id")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
  }
  /** Collection-like TableQuery object for table GroupUser */
  lazy val GroupUser = new TableQuery(tag => new GroupUser(tag))

  /** Entity class storing rows of table IbukiGroup
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param groupId Database column group_id SqlType(text)
   *  @param groupName Database column group_name SqlType(text)
   *  @param ownerId Database column owner_id SqlType(text)
   *  @param date Database column date SqlType(date) */
  case class IbukiGroupRow(id: Int, groupId: String, groupName: String, ownerId: String, date: java.sql.Date)
  /** GetResult implicit for fetching IbukiGroupRow objects using plain SQL queries */
  implicit def GetResultIbukiGroupRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[IbukiGroupRow] = GR{
    prs => import prs._
    IbukiGroupRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table ibuki_group. Objects of this class serve as prototypes for rows in queries. */
  class IbukiGroup(_tableTag: Tag) extends Table[IbukiGroupRow](_tableTag, "ibuki_group") {
    def * = (id, groupId, groupName, ownerId, date) <> (IbukiGroupRow.tupled, IbukiGroupRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(groupId), Rep.Some(groupName), Rep.Some(ownerId), Rep.Some(date)).shaped.<>({r=>import r._; _1.map(_=> IbukiGroupRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column group_id SqlType(text) */
    val groupId: Rep[String] = column[String]("group_id")
    /** Database column group_name SqlType(text) */
    val groupName: Rep[String] = column[String]("group_name")
    /** Database column owner_id SqlType(text) */
    val ownerId: Rep[String] = column[String]("owner_id")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
  }
  /** Collection-like TableQuery object for table IbukiGroup */
  lazy val IbukiGroup = new TableQuery(tag => new IbukiGroup(tag))

  /** Entity class storing rows of table IbukiUser
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(text)
   *  @param userName Database column user_name SqlType(text)
   *  @param email Database column email SqlType(text)
   *  @param date Database column date SqlType(date) */
  case class IbukiUserRow(id: Int, userId: String, userName: String, email: String, date: java.sql.Date)
  /** GetResult implicit for fetching IbukiUserRow objects using plain SQL queries */
  implicit def GetResultIbukiUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[IbukiUserRow] = GR{
    prs => import prs._
    IbukiUserRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table ibuki_user. Objects of this class serve as prototypes for rows in queries. */
  class IbukiUser(_tableTag: Tag) extends Table[IbukiUserRow](_tableTag, "ibuki_user") {
    def * = (id, userId, userName, email, date) <> (IbukiUserRow.tupled, IbukiUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(userName), Rep.Some(email), Rep.Some(date)).shaped.<>({r=>import r._; _1.map(_=> IbukiUserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(text) */
    val userId: Rep[String] = column[String]("user_id")
    /** Database column user_name SqlType(text) */
    val userName: Rep[String] = column[String]("user_name")
    /** Database column email SqlType(text) */
    val email: Rep[String] = column[String]("email")
    /** Database column date SqlType(date) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("date")
  }
  /** Collection-like TableQuery object for table IbukiUser */
  lazy val IbukiUser = new TableQuery(tag => new IbukiUser(tag))
}
