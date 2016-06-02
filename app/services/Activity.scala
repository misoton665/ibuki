package services

import services.ActionTag.ActionTag
import services.User.User

object Activity {

  trait Action {
    val contributor: User
    val tags: Array[ActionTag]
    val body: String
  }

  // RootAction explain to Activity that is included it.
  case class RootAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.rootTag +: initTags
  }

  // DocumentAction is main content of Activity.
  case class DocumentAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = initTags
  }

  // CommentAction is contributed by expect for Activity contributor.
  // It is a Action to mention to other Action.
  case class CommentAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.commentTag +: initTags
  }

  // QuestionAction is contributed by expect for Activity contributor.
  // It is a Action to question to other Action.
  case class QuestionAction(contributor: User, initTags: Array[ActionTag], body: String) extends Action {
    val tags = ActionTag.questionTag +: initTags
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