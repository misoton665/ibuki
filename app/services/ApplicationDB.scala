package services

import services.Contribution.Action
import services.ErrorMessageGenerator.ErrorMessage
import services.Organization.{Email, User}

object ApplicationDB {
  sealed trait DBResult[A]
  case class DBSuccess[A](result: A) extends DBResult[A]
  case class DBError(errorMessage: ErrorMessage) extends DBResult

  def createAction(action: Action): DBResult[Action] = {
    val dateString = DateConverter.generateNowDateString()

    val actionWithDate = action match {
      case Action(c, t, b, None) => Action(c, t, b, Some(dateString))
      case a @ Action(_, _, _, Some(_)) => a
    }

    // TODO: register into the DB

    DBSuccess(actionWithDate)
  }

  def searchUserById(userId: String): User = {
    User(userId, userId + "_name", Email(userId + "@" + userId + ".com"), None)
  }
}
