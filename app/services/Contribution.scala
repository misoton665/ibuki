package services

import play.api.libs.json.{JsObject, Json}
import services.ActionTag.ActionTag
import services.ApplicationObject.{JsonReadable, Jsonable}
import services.Organization.User

object Contribution {

  sealed abstract class ActionType(val typeName: String)
  case object RootActionType extends ActionType("root")
  case object DocumentActionType extends ActionType("document")
  case object CommentActionType extends ActionType("comment")
  case object QuestionActionType extends ActionType("question")

  trait Action extends Jsonable{
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

  case object Action extends JsonReadable[Action] {
    private val keyContributorId = "contributor_id"
    private val keyActionType = "actionType"
    private val keyTags = "tags"
    private val keyBody = "body"

    override def readJson(jsonString: String): Option[Action] = {
      val json = Json.parse(jsonString)
      val parsedValue = List(keyContributorId, keyActionType, keyBody).map((key) => (json \ key).asOpt[String])
      val parsedTags = (json \ keyTags).asOpt[Array[String]]

      def generateAction(contributorId: String, actionType: String, tags: Array[String], body: String): Option[Action] = {
        val user = ApplicationDB.searchUserById(contributorId)
        val actionTags = tags.map(ActionTag.tag)

        actionType match {
          case RootActionType.typeName => Some(RootAction(user, actionTags, body))
          case DocumentActionType.typeName => Some(DocumentAction(user, actionTags, body))
          case CommentActionType.typeName => Some(CommentAction(user, actionTags, body))
          case QuestionActionType.typeName => Some(QuestionAction(user, actionTags, body))
          case _ => None
        }
      }

      (parsedValue, parsedTags) match {
        case (List(Some(contributor_id), Some(actionType), Some(body)), Some(tags)) => generateAction(contributor_id, actionType, tags, body)
        case _ => None
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
  case class Activity(actions: Array[Action]) {
    val rootAction: Option[Action] = actions match {
      case Array(h@RootAction(_, _, _), _*) => Some(h)
      case _ => None
    }

    val isInvalidActivity: Boolean = rootAction.isEmpty
  }
}