package controllers

import javax.inject.Inject

import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller}
import services.ApplicationDB

class CreationController @Inject() extends Controller {

  private sealed trait ErrorType
  private case object UrlError extends ErrorType

  def create(target: String) = Action { request =>
    val jsonBody: Option[JsValue] = request.body.asJson

    val result = (target, jsonBody) match {
      case ("activity", Some(js)) => createActivity(js)
      case ("action", Some(js))   => createAction(js)
      case _          => generateErrorMessage(UrlError)
    }

    Ok(result)
  }

  private def createActivity(json: JsValue): String = {
    "createActivity"
  }

  private def createAction(json: JsValue): String = {
    val newAction = json.asOpt[services.Contribution.Action]

    val result = newAction match {
      case Some(action) => ApplicationDB.createAction(action)
      case None => None
    }
    "createAction"
  }

  private def generateErrorMessage(errorType: ErrorType): String = {
    errorType match {
      case UrlError => "url error"
      case _        => "server error"
    }
  }

}