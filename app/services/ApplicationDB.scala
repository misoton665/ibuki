package services

import services.Organization.{Email, User}

object ApplicationDB {

  def searchUserById(userId: String): User = {
    User(userId, userId + "_name", Email(userId + "@" + userId + ".com"))
  }
}
