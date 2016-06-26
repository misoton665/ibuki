package services

import play.api.libs.json.{JsValue, Json}

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import scala.util.Try

object ErrorMessage {
  private val keyErrorMessage = "errorMessage"

  val MESSAGE_INVALID_JSON = "Invalid json"
  val MESSAGE_DB_ERROR = "DB error"
  val MESSAGE_API_NOT_FOUND = "Api not found"

  case class ErrorMessage(json: JsValue)

  sealed trait ApiResult[+A] {
    def getAsFuture: Future[A]
  }
  case class ApiSuccess[A](future: Future[A]) extends ApiResult[A] {
    def getAsFuture: Future[A] = future
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
