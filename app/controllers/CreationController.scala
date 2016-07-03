package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ErrorMessage._
import models.{ActivityRepo, IbukiGroupRepo, IbukiUserRepo, TableRepository}
import scala.concurrent.Future

class CreationController @Inject() (activityRepo: ActivityRepo, ibukiGroupRepo: IbukiGroupRepo, ibukiUserRepo: IbukiUserRepo) extends Controller {

  def create(target: String) = Action.async(BodyParsers.parse.json) { request =>
    val jsonBody: JsValue = request.body

    val result: ApiResult[String] = (target, jsonBody) match {
      // /create/activity
      case ("activity", js) => createActivity(js)

      // /create/action
      case ("action", js)   => createAction(js)

      // /create/group
      case ("group", js)    => createGroup(js)

      // /create/user
      case ("user", js) => createUser(js)
      // otherwise it make an error that api not found.
      case _ => ApiError(generateError(MESSAGE_API_NOT_FOUND))
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

    extractStringElements(json, parameterKeys, {
        case List(Some(activityName), Some(userId), Some(groupId)) =>
          val createProcess = activityRepo.createActivity(activityName, userId, groupId).map{
            case Some(i) => i.toString
            case None => generateError(MESSAGE_SOME_IDS_HAVE_NOT_REGISTERED).json.toString
          }
          ApiSuccess(createProcess)
        case _ => ApiError(generateError(MESSAGE_INVALID_JSON))
      }
    )
  }

  private def createAction(json: JsValue): ApiResult[String] = {
    ApiError(generateError(MESSAGE_INVALID_JSON))
  }

  private def createGroup(json: JsValue): ApiResult[String] = {
    val parameterKeys = List("groupId", "groupName", "ownerId")

    extractStringElements(json, parameterKeys, {
        case List(Some(groupId), Some(groupName), Some(ownerId)) =>
          ApiSuccess(ibukiGroupRepo.createIbukiGroup(groupId, groupName, ownerId).map(_.toString))
        case _ => ApiError(generateError(MESSAGE_INVALID_JSON))
      }
    )
  }

  private def createUser(json: JsValue): ApiResult[String] = {
    val parameterKeys = List("userId", "userName", "email")

    extractStringElements(json, parameterKeys, {
        case List(Some(userId), Some(userName), Some(email)) =>
          val createProcess: Future[String] = ibukiUserRepo.createIbukiUser(userId, userName, email).map{
            case Some(i) => i.toString
            case None => generateError(MESSAGE_VALUE_IS_NOT_FOUND(email)).json.toString
          }
          ApiSuccess(createProcess)
        case _ => ApiError(generateError(MESSAGE_INVALID_JSON))
      }
    )
  }

  private def extractStringElements[T <: TableRepository[_, _]]
  (json: JsValue, keys: List[String], processIfSuccess: List[Option[String]] => ApiResult[String]): ApiResult[String] ={
    val parameters: List[Option[String]] = keys.map {key =>
      (json \ key).asOpt[String].map(_.toString)
    }

    val isValidJson: Boolean = parameters.forall(_.isDefined)

    if (isValidJson) {
      processIfSuccess(parameters)
    } else {
      ApiError(generateError(MESSAGE_INVALID_JSON))
    }
  }
}
