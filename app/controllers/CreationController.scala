package controllers

import javax.inject.Inject

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ApplicationDB.{DBError, DBSuccess}
import services.{ApplicationDB, ErrorMessage}

class CreationController @Inject() extends Controller {

  private sealed trait ErrorType
  private case object UrlError extends ErrorType

  def create(target: String) = Action(BodyParsers.parse.json) { request =>
    val jsonBody: JsValue = request.body

    val result = (target, jsonBody) match {
      case ("activity", js) => createActivity(js)
      case ("action", js)   => createAction(js)
      case _          => generateErrorMessage(UrlError)
    }

    Ok(result)
  }

  private def createActivity(json: JsValue): String = {
    val newActivity = json.asOpt[services.Contribution.Activity]

    val result = newActivity.map(ApplicationDB.createActivity).map {
      case DBSuccess(action) => Json.toJson(action)
      case DBError(message) => message.json
    }

    result match {
      case Some(j) => j.toString
      case None => ErrorMessage.generateError(ErrorMessage.INVALID_JSON).json.toString
    }
  }

  private def createAction(json: JsValue): String = {
    val newAction = json.asOpt[services.Contribution.Action]

    val result = newAction.map(ApplicationDB.createAction).map {
      case DBSuccess(action) => Json.toJson(action)
      case DBError(message) => message.json
    }

    result match {
      case Some(j) => j.toString
      case None => ErrorMessage.generateError(ErrorMessage.INVALID_JSON).json.toString
    }
  }

  private def generateErrorMessage(errorType: ErrorType): String = {
    errorType match {
      case UrlError => "url error"
      case _        => "server error"
    }
  }

}