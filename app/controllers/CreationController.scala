package controllers

import javax.inject.Inject

import play.api.libs.json.JsValue
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ErrorMessage

class CreationController @Inject() extends Controller {

  def create(target: String) = Action(BodyParsers.parse.json) { request =>
    val jsonBody: JsValue = request.body

    val result = (target, jsonBody) match {
      // /create/activity
      case ("activity", js) => createActivity(js)

      // /create/action
      case ("action", js)   => createAction(js)

      // otherwise it will occur error that an api not found.
      case _          => ErrorMessage.generateError(ErrorMessage.API_NOT_FOUND).json.toString()
    }

    Ok(result)
  }

  private def createActivity(json: JsValue): String = {
    "activity"
  }

  private def createAction(json: JsValue): String = {
    "action"
  }

}
