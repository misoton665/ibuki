package services

object ActionTag {
  def tag = AttributeTag
  val rootTag = AttributeTag("root")
  val documentTag = AttributeTag("document")
  val commentTag = AttributeTag("comment")
  val questionTag = AttributeTag("question")

  case class AttributeTag(name: String)
}