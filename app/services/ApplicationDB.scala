package services

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json._
import services.Contribution.{Action, Activity, RootAction}
import services.ErrorMessage.ErrorMessage
import services.Organization.{Email, User}

object ApplicationDB {

  sealed trait DBResult[+A] {
    def map[B](f: DBResult[A] => DBResult[B]): DBResult[B] = {
      f(this)
    }
  }

  case class DBSuccess[A](result: A) extends DBResult[A]

  case class DBError(errorMessage: ErrorMessage = ErrorMessage.generateError(ErrorMessage.DB_ERROR)) extends DBResult[Nothing] {
    def getValueAsJson: JsValue = errorMessage.json
  }

  def createAction(action: Action): DBResult[Action] = {
    val dateString = DateConverter.generateNowDateString()

    val actionWithDate = action match {
      case Action(c, t, b, None) => Action(c, t, b, Some(dateString))
      case a @ Action(_, _, _, Some(_)) => a
    }

    // TODO: register into the DB

    DBSuccess(actionWithDate)
  }

  def createActivity(activity: Activity): DBResult[Activity] = {
    createAction(activity.rootAction).map {
      case DBSuccess(action @ RootAction(_, _, _, _)) => DBSuccess(Activity(action))
      case err @ DBError(_) => err
    }
  }

  def searchUserById(userId: String): User = {
    User(userId, userId + "_name", Email(userId + "@" + userId + ".com"), None)
  }
}
