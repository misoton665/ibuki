package services

object ActionTag {
  def tag = ActionTag
  val rootTag = ActionTag("root")
  val commentTag = ActionTag("comment")
  val questionTag = ActionTag("question")

  case class ActionTag(name: String)
}