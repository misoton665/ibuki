package controllers

import javax.inject.Inject

import play.api.libs.json.{JsResult, JsValue}
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

      // otherwise it will be occurred error that an api not found.
      case _ => ErrorMessage.generateError(ErrorMessage.API_NOT_FOUND).json.toString()
    }

    Ok(result)
  }

  /**
    * createActivity (/contribution/activity)
    *
    * parameter JSON
    *  activity_name: string - Activity name
    *  user_id: string       - Contributor name
    *  group_id: string      - A group which the activity is going to contributed to
    *
    * return JSON
    * OK:
    *   activity_id: string - Created activity ID
    *   date: string        - A Date when activity created
    *
    * NG:
    *   Error message JSON by ErrorMessage
    *
    * @param json JSON string by request body
    * @return return JSON
    */
  private def createActivity(json: JsValue): String = {
    val parameterKeys = List("activity_name", "user_id", "group_id")
    val parameters: List[JsResult[String]] = parameterKeys.map { key =>
      (json \ key).validate[String]
    }
    val validJson: Boolean = !parameters.contains((res: JsResult[String]) => res.isError)

    if (validJson) {

    }
    "activity"
  }

  private def createAction(json: JsValue): String = {
    "action"
  }

}
