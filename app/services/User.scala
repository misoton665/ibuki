package services

import services.Activity.Activity

object User {
  case class User(id: String, name: String, email: Email, isOwner: Boolean = false)

  case class Email(email: String) {
    val isValidEmail: Boolean = email.matches("""^[0-9a-z_./?-]+@([0-9a-z-]+\.)+[0-9a-z-]+$""")
  }

  case class Group(id: String, name: String, owner: User, members: Array[User], activities: Array[Activity])
}