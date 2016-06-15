package services

import services.Contribution.Action
import services.Organization.{Email, User}

object ApplicationDB {

  def createAction(action: Action): Option[Action] = {
    val dateString = DateConverter.generateNowDateString()

    val actionWithDate = action match {
      case Action(c, t, b, None) => Action(c, t, b, Some(dateString))
      case a @ Action(_, _, _, Some(_)) => a
    }

    // TODO: register into the DB

    Some(actionWithDate)
  }

  def searchUserById(userId: String): User = {
    User(userId, userId + "_name", Email(userId + "@" + userId + ".com"))
  }
}
