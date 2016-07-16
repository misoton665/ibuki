package services

import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ResultMessage {
  val MESSAGE_INVALID_JSON = "Invalid json"
  val MESSAGE_DB_ERROR = "DB error"
  val MESSAGE_API_NOT_FOUND = "Api is not found"
  val MESSAGE_SOME_IDS_HAVE_NOT_REGISTERED = "Some ids have not registered"
  val MESSAGE_VALUE_IS_NOT_FOUND = (value: String) => s"$value is not found"

  case class ErrorMessage(json: JsValue)

  private val keyErrorMessage = "errorMessage"

  sealed trait ApiResult[+A] {
    def getAsFuture: Future[A]
  }
  case class ApiSuccess[A](future: Future[A]) extends ApiResult[A] {
    override def getAsFuture: Future[A] = future
  }

  case class ApiError(errorMessage: ErrorMessage) extends ApiResult[String] {
    override def getAsFuture: Future[String] = Future[String]{errorMessage.json.toString}
  }

  def generateError(errorMessage: String): ErrorMessage = {
    ErrorMessage(
      Json.obj(keyErrorMessage -> errorMessage)
    )
  }
}
