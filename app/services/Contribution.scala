package services

import play.api.libs.json._
import services.ActionTag.AttributeTag
import services.Organization.User

object Contribution {

  sealed abstract class ActionType(val typeName: String)
  case object RootActionType extends ActionType("root")
  case object DocumentActionType extends ActionType("document")
  case object CommentActionType extends ActionType("comment")
  case object QuestionActionType extends ActionType("question")

  sealed trait Action {
    val contributor: User
    val actionType: ActionType
    val tags: Array[AttributeTag]
    val body: String
    val date: Option[String]
  }

  case object Action {
    val keyContributorId = "contributor_id"
    val keyActionType = "action_type"
    val keyTags = "tags"
    val keyBody = "body"

    def apply(contributor: User, tags: Array[AttributeTag], body: String, date: Option[String]): Action = {

      tags match {
        case Array(ActionTag.rootTag, _*) => RootAction(contributor, tags, body, date)
        case Array(ActionTag.commentTag, _*) => CommentAction(contributor, tags, body, date)
        case Array(ActionTag.questionTag, _*) => QuestionAction(contributor, tags, body, date)
        case _ => DocumentAction(contributor, tags, body, date)
      }
    }

    def unapply(action: Action): Option[(User, Array[AttributeTag], String, Option[String])] = {
      Some((action.contributor, action.tags, action.body, action.date))
    }
  }

  implicit val actionReads = new Reads[Action] {
    override def reads(js: JsValue): JsResult[Action] = {
      val json = js
      val parsedValue = List(Action.keyContributorId, Action.keyActionType, Action.keyBody).map((key) => (json \ key).asOpt[String])
      val parsedTags = (json \ Action.keyTags).asOpt[Array[String]]

      def generateAction(contributorId: String, actionType: String, tags: Array[String], body: String): Action = {
        val user = ApplicationDB.searchUserById(contributorId)
        val actionTags = tags.map(ActionTag.tag)

        Action(user, actionTags, body, None)
      }

      (parsedValue, parsedTags) match {
        case (List(Some(contributorId_), Some(actionType_), Some(body_)), Some(tags_)) => JsSuccess(generateAction(contributorId_, actionType_, tags_, body_))
        case _ => JsError()
      }
    }
  }

  implicit val actionWrites = new Writes[Action] {
    override def writes(action: Action): JsValue = {
      Json.obj(
        Action.keyContributorId -> action.contributor.id,
        Action.keyActionType -> action.actionType.typeName,
        Action.keyTags -> action.tags.map(_.name),
        Action.keyBody -> action.body
      )
    }
  }


  // RootAction explain to Activity that is included it.
  case class RootAction(contributor: User, initTags: Array[AttributeTag], body: String, date: Option[String]) extends Action {
    val tags = ActionTag.rootTag +: initTags
    val actionType = RootActionType
  }

  // DocumentAction is main content of Activity.
  case class DocumentAction(contributor: User, initTags: Array[AttributeTag], body: String, date: Option[String]) extends Action {
    val tags = initTags
    val actionType = DocumentActionType
  }

  // CommentAction is contributed by expect for Activity contributor.
  // It is a Action to mention to other Action.
  case class CommentAction(contributor: User, initTags: Array[AttributeTag], body: String, date: Option[String]) extends Action {
    val tags = ActionTag.commentTag +: initTags
    val actionType = CommentActionType
  }

  // QuestionAction is contributed by expect for Activity contributor.
  // It is a Action to question to other Action.
  case class QuestionAction(contributor: User, initTags: Array[AttributeTag], body: String, date: Option[String]) extends Action {
    val tags = ActionTag.questionTag +: initTags
    val actionType = QuestionActionType
  }

  // Explanation of Activity is included in its root action: head of actions that the activity has.
  // If head of actions is not RootAction, its Activity is invalid.
  case class Activity(rootAction: Action) {
    require(rootAction.isInstanceOf[RootAction])
  }

  case object Activity {
    val keyRootAction = "root_action"
  }

  implicit val activityReads = new Reads[Activity] {
    override def reads(js: JsValue): JsResult[Activity] = {
      val rootActionJson = (js \ Activity.keyRootAction).asOpt[Action]

      rootActionJson match {
        case Some(rootAction) => JsSuccess(Activity(rootAction))
        case None => JsError()
      }
    }
  }

  implicit val activityWrites = new Writes[Activity] {
    override def writes(activity: Activity): JsValue = {
      val rootActionJson = Json.toJson(activity.rootAction)

      Json.obj(
        Activity.keyRootAction -> rootActionJson
      )
    }
  }
}