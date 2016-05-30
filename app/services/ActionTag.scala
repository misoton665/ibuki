package services

trait ActionTag{
  val name: String
}

case class AttributeTag(name: String) extends ActionTag

case object ActionTag {
  def tag = AttributeTag
  val rootTag = AttributeTag("root")
  val commentTag = AttributeTag("comment")
  val questionTag = AttributeTag("question")
}