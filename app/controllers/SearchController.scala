package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}

class SearchController @Inject() extends Controller {

  private sealed trait ErrorType
  private case object UrlError extends ErrorType

  def search(target: String) = Action {

    val result = target match {
      case "activity" => searchActivity()
      case _ => generateErrorMessage(UrlError)
    }

    Ok(result)
  }

  def searchActivity(): String = {
    "activity"
  }

  def searchAction(): String = {
    "action"
  }

  def searchUser(): String = {
    "user"
  }

  def searchGroup(): String = {
    "group"
  }

  def generateErrorMessage(errorType: ErrorType): String = {
    errorType match {
      case UrlError => "url error"
      case _        => "server error"
    }
  }

}
