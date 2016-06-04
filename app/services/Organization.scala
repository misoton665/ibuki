package services

import play.api.libs.json.{JsObject, Json}
import services.Contribution.Activity
import services.ApplicationObject.{JsonReadable, Jsonable}

object Organization {
  case class User(id: String, name: String, email: Email, isOwner: Boolean = false) extends Jsonable {
    override def toJson: JsObject = Json.obj(
      User.keyUserId -> id,
      User.keyUserName -> name,
      User.keyEmail -> email.address
    )
  }

  case object User extends JsonReadable[User] {
    val keyUserId = "user_id"
    val keyUserName = "user_name"
    val keyEmail = "email"

    def readJson(jsonString: String): Option[User] =  {
      val json = Json.parse(jsonString)
      val parsedValue = List(keyUserId, keyUserName, keyEmail).map((key) => (json \ key).asOpt[String])

      parsedValue match {
        case List(Some(id), Some(name), Some(email)) => Some(new User(id, name, Email(email)))
        case _ => None
      }
    }
  }

  case class Email(address: String) {
    val isValidEmail: Boolean = address.matches("""^[0-9a-z_./?-]+@([0-9a-z-]+\.)+[0-9a-z-]+$""")
  }

  case class Group(id: String, name: String, owner: User, members: Array[User], activities: Array[Activity])
}