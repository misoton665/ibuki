package services

import play.api.libs.json.{JsObject, Json}
import services.Activity.Activity
import services.ApplicationObject.{JsonReadable, Jsonable}

object Organization {
  case class User(id: String, name: String, email: Email, isOwner: Boolean = false) extends Jsonable {
    override def toJson: JsObject = Json.obj(
      "user_id" -> id,
      "user_name" -> name,
      "email" -> email.address
    )
  }

  case object User extends JsonReadable[User] {
    def readJson(jsonString: String): Option[User] =  {
      val json = Json.parse(jsonString)
      val parsedValue = List("user_id", "user_name", "email").map((key) => (json \ key).asOpt[String])

      parsedValue match {
        case List(Some(pid), Some(pname), Some(pemail)) => Some(new User(pid, pname, Email(pemail)))
        case _ => None
      }
    }
  }

  case class Email(address: String) {
    val isValidEmail: Boolean = address.matches("""^[0-9a-z_./?-]+@([0-9a-z-]+\.)+[0-9a-z-]+$""")
  }

  case class Group(id: String, name: String, owner: User, members: Array[User], activities: Array[Activity])
}