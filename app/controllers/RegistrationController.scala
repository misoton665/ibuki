package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.mvc.{Action, BodyParsers, Controller, Result}
import services.ResultMessage._
import models.{ActivityRepo, IbukiGroupRepo, IbukiUserRepo, TableRepository}
import scala.concurrent.Future

class RegistrationController @Inject()(activityRepo: ActivityRepo, ibukiGroupRepo: IbukiGroupRepo, ibukiUserRepo: IbukiUserRepo) extends Controller {

  def register(target: String) = Action.async(BodyParsers.parse.json) { request =>
    val jsonBody: JsValue = request.body

    (target, jsonBody) match {
      // /register/activity
      case ("activity", js) => registerActivity(js)

      // /register/action
      case ("action", js)   => registerAction(js)

      // /register/group
      case ("group", js)    => registerGroup(js)

      // /register/user
      case ("user", js) => registerUser(js)

      // otherwise it make an error that api not found.
      case _ => Future(BadRequest(generateError(MESSAGE_API_NOT_FOUND).json.toString))
    }
  }

  /**
    * registerActivity (/register/activity)
    *
    * parameter JSON
    *  activity_name: string - Activity name
    *  user_id: string       - Contributor name
    *  group_id: string      - A group which the activity is going to contributed to
    *
    * return JSON
    * OK:
    *   activity_id: string - registerd activity ID
    *   date: string        - A Date when activity registerd
    *
    * NG:
    *   Error message JSON by ErrorMessage
    *
    * @param json JSON string by request body
    * @return return JSON
    */
  private def registerActivity(json: JsValue) = {
    val parameterKeys = List("activity_name", "user_id", "group_id")

    extractStringElements(json, parameterKeys, {
        case Some(List(activityName, userId, groupId)) =>
          activityRepo.insertActivity(activityName, userId, groupId).map{
            case Right(id) => Ok(id.toString)
            case Left(repoMsg) => BadRequest(generateError(MESSAGE_TABLE_REPOSITORY_ERROR(repoMsg.message)).json.toString)
          }
        case _ => Future(BadRequest(generateError(MESSAGE_INVALID_JSON).json.toString))
      }
    )
  }

  private def registerAction(json: JsValue) = {
    Future(BadRequest(generateError(MESSAGE_INVALID_JSON).json.toString))
  }

  private def registerGroup(json: JsValue) = {
    val parameterKeys = List("groupId", "groupName", "ownerId")

    extractStringElements(json, parameterKeys, {
        case Some(List(groupId, groupName, ownerId)) =>
          ibukiGroupRepo.insertIbukiGroup(groupId, groupName, ownerId).map {
            case Right(id) => Ok(id.toString)
            case Left(repoMsg) => BadRequest(generateError(MESSAGE_TABLE_REPOSITORY_ERROR(repoMsg.message)).json.toString)
          }
        case _ => Future(BadRequest(generateError(MESSAGE_INVALID_JSON).json.toString))
      }
    )
  }

  private def registerUser(json: JsValue) = {
    val parameterKeys = List("userId", "userName", "email")

    extractStringElements(json, parameterKeys, {
        case Some(List(userId, userName, email)) =>
          ibukiUserRepo.insertIbukiUser(userId, userName, email).map{
            case Right(id) => Ok(id.toString)
            case Left(repoMsg) => BadRequest(generateError(MESSAGE_TABLE_REPOSITORY_ERROR(repoMsg.message)).json.toString)
          }
        case _ => Future(BadRequest(generateError(MESSAGE_INVALID_JSON).json.toString))
      }
    )
  }

  private def extractStringElements[T <: TableRepository[_, _]]
  (json: JsValue, keys: List[String], processIfSuccess: Option[List[String]] => Future[Result]): Future[Result] ={
    val parameters: List[Option[String]] = keys.map {key =>
      (json \ key).asOpt[String].map(_.toString)
    }

    def map2[A,B,C](a: Option[A], b: Option[B])(f: (A,B) => C): Option[C] =
      a.flatMap { x => b.map { y => f(x, y) } }

    def sequence[A](a: List[Option[A]]): Option[List[A]] =
      a.foldRight[Option[List[A]]](Some(List()))((x,y) => map2(x,y)(_ :: _))

    val isValidJson: Boolean = parameters.forall(_.isDefined)

    if (isValidJson) {
      processIfSuccess(sequence(parameters))
    } else {
      Future(BadRequest(generateError(MESSAGE_INVALID_JSON).json.toString))
    }
  }
}
