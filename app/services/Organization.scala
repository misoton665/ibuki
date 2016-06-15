package services

import play.api.libs.json._
import services.Contribution.Activity

object Organization {
  case class User(id: String, name: String, email: Email, date: Option[String], isOwner: Boolean = false)

  case object User {
    val keyUserId = "user_id"
    val keyUserName = "user_name"
    val keyEmail = "email"
  }

  implicit val userReads = new Reads[User] {
    def reads(js: JsValue): JsResult[User] = {
      val json = js

      val parsedValue = List(User.keyUserId, User.keyUserName, User.keyEmail).map((key) => (json \ key).asOpt[String])

      parsedValue match {
        case List(Some(id), Some(name), Some(email)) => JsSuccess(new User(id, name, Email(email), None))
        case _ => JsError()
      }
    }
  }

  implicit val userWrites = new Writes[User] {
    def writes(user: User): JsValue = {
      Json.obj(
        User.keyUserId -> user.id,
        User.keyUserName -> user.name,
        User.keyEmail -> user.email.address
      )
    }
  }

  case class Email(address: String) {
    val isValidEmail: Boolean = address.matches("""^[0-9a-z_./?-]+@([0-9a-z-]+\.)+[0-9a-z-]+$""")
  }

  case class Group(id: String, name: String, owner: User, members: Array[User], activities: Array[Activity])
}