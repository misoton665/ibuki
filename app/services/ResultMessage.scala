package services

import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc.Results.{Ok, BadRequest}
import play.api.mvc.Result

object ResultMessage {
  val MESSAGE_INVALID_JSON = "Invalid json"
  val MESSAGE_DB_ERROR = "DB error"
  val MESSAGE_API_NOT_FOUND = "Api is not found"
  val MESSAGE_SOME_IDS_HAVE_NOT_REGISTERED = "Some ids have not registered"
  val MESSAGE_VALUE_IS_NOT_FOUND = (value: String) => s"$value is not found"
  val MESSAGE_TABLE_REPOSITORY_ERROR = (message: String) => s"$message"

  case class ErrorMessage(json: JsValue)

  private val keyErrorMessage = "errorMessage"

  sealed trait ApiResult {
    def getAsFuture: Future[Result]
  }

  case class ApiSuccess(future: Future[String]) extends ApiResult {
    override def getAsFuture: Future[Result] = future.map{Ok(_)}
  }

  case class ApiError(errorMessage: ErrorMessage) extends ApiResult {
    override def getAsFuture: Future[Result] = Future[Result]{BadRequest(errorMessage.json.toString)}
  }

  def generateError(errorMessage: String): ErrorMessage = {
    ErrorMessage(
      Json.obj(keyErrorMessage -> errorMessage)
    )
  }
}
