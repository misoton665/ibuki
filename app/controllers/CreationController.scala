package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ErrorMessage._
import models.ActivityRepo

class CreationController @Inject() (activityRepo: ActivityRepo) extends Controller {

  def create(target: String) = Action.async(BodyParsers.parse.json) { request =>
    val jsonBody: JsValue = request.body

    val result: ApiResult[String] = (target, jsonBody) match {
      // /create/activity
      case ("activity", js) => createActivity(js)

      // /create/action
      case ("action", js)   => createAction(js)

      // otherwise it will be occurred error that an api not found.
      case _ => ApiError(generateError(API_NOT_FOUND))
    }

    result.getAsFuture.map(v => Ok(v))
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
  private def createActivity(json: JsValue): ApiResult[String] = {
    val parameterKeys = List("activity_name", "user_id", "group_id")
    val parameters: List[Option[String]] = parameterKeys.map { key =>
      (json \ key).asOpt[String]
    }

    val isValidJson: Boolean = parameters.forall(_.isDefined)

    if (isValidJson) {
      parameters match {
        case List(Some(activityName), Some(userId), Some(groupId)) =>
          ApiSuccess(activityRepo.create(activityName, userId, groupId).map(_.toString))

        case _ => ApiError(generateError(INVALID_JSON))
      }
    } else {
      ApiError(generateError(INVALID_JSON))
    }
  }

  private def createAction(json: JsValue): ApiResult[String] = {
    ApiError(generateError(INVALID_JSON))
  }

}
