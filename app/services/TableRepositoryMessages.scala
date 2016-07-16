package services

object TableRepositoryMessages {
  trait TableRepositoryMessage {
    val message: String = "no message"
  }

  case object ValidationErrorMessage extends TableRepositoryMessage {
    override val message: String = "validation error"
  }
}
