package services

import play.api.libs.json._
import services.ActionTag.ActionTag
import services.ApplicationObject.{JsonReadable, Jsonable}
import services.Organization.User

object Contribution {

  sealed abstract class ActionType(val typeName: String)
  case object RootActionType extends ActionType("root")
  case object DocumentActionType extends ActionType("document")
  case object CommentActionType extends ActionType("comment")
  case object QuestionActionType extends ActionType("question")

  sealed trait Action extends Jsonable {
    val contributor: User
    val actionType: ActionType
    val tags: Array[ActionTag]
    val body: String

    override def toJson: JsObject = Json.obj(
      Action.keyContributorId -> contributor.id,
      Action.keyActionType -> actionType.typeName,
      Action.keyTags -> Json.arr(tags.map(_.name)),
      Action.keyBody -> body
    )
  }

  case object Action {
    val keyContributorId = "contributor_id"
    val keyActionType = "action_type"
    val keyTags = "tags"
    val keyBody = "body"
  }

  implicit val actionReads = new Reads[Action] {
    override def reads(js: JsValue): JsResult[Action] = {
      val json = js
      val parsedValue = List(Action.keyContributorId, Action.keyActionType, Action.keyBody).map((key) => (json \ key).asOpt[String])
      val parsedTags = (json \ Action.keyTags).asOpt[Array[String]]

      def generateAction(contributorId: String, actionType: String, tags: Array[String], body: String): JsResult[Action] = {
        val user = ApplicationDB.searchUserById(contributorId)
        val actionTags = tags.map(ActionTag.tag)

        actionType match {
          case RootActionType.typeName => JsSuccess(RootAction(user, actionTags, body))
          case DocumentActionType.typeName => JsSuccess(DocumentAction(user, actionTags, body))
          case CommentActionType.typeName => JsSuccess(CommentAction(user, actionTags, body))
          case QuestionActionType.typeName => JsSuccess(QuestionAction(user, actionTags, body))
          case _ => JsError()
        }
      }

      (parsedValue, parsedTags) match {
        case (List(Some(contributor_id_), Some(actionType_), Some(body_)), Some(tags_)) => generateAction(contributor_id_, actionType_, tags_, body_)
        case _ => JsError()
      }
    }
  }


  // RootAction explain to Activity that is included it.
  case class RootAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.rootTag +: initTags
    val actionType = RootActionType
  }

  // DocumentAction is main content of Activity.
  case class DocumentAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = initTags
    val actionType = DocumentActionType
  }

  // CommentAction is contributed by expect for Activity contributor.
  // It is a Action to mention to other Action.
  case class CommentAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.commentTag +: initTags
    val actionType = CommentActionType
  }

  // QuestionAction is contributed by expect for Activity contributor.
  // It is a Action to question to other Action.
  case class QuestionAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.questionTag +: initTags
    val actionType = QuestionActionType
  }

  // Explanation of Activity is included in its root action: head of actions that the activity has.
  // If head of actions is not RootAction, its Activity is invalid.
  case class Activity(actions: Array[Action]) extends Jsonable {
    val rootAction: Option[Action] = actions match {
      case Array(h@RootAction(_, _, _), _*) => Some(h)
      case _ => None
    }

    val isInvalidActivity: Boolean = rootAction.isEmpty

    override def toJson: JsObject = {
      val rootActionJson = this.rootAction match {
        case Some(action) => action.toJson
        case None => JsNull
      }

      Json.obj(
        "root_action" -> rootActionJson
      )
    }
  }

  case object Activity extends JsonReadable[Activity]{
    override def readJson(jsonString: String): Option[Activity] = {
      val actionsJson = (Json.parse(jsonString) \ "actions").asOpt[Array[Action]]

      actionsJson match {
        case Some(actions) => Some(Activity(actions))
        case None => None
      }
    }
  }
}