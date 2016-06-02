package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}

class CreationController @Inject() extends Controller {

  private sealed trait ErrorType
  private case object UrlError extends ErrorType

  def create(target: String) = Action {

    val result = target match {
      case "activity" => createActivity()
      case "action"   => createAction()
      case _          => generateErrorMessage(UrlError)
    }

    Ok(result)
  }

  private def createActivity(): String = {
    "createActivity"
  }

  private def createAction(): String = {
    "createAction"
  }

  private def generateErrorMessage(errorType: ErrorType): String = {
    errorType match {
      case UrlError => "url error"
      case _        => "server error"
    }
  }

}