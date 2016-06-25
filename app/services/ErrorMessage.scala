package services

import play.api.libs.json.{JsValue, Json}

object ErrorMessage {
  val keyErrorMessage = "errorMessage"
  val INVALID_JSON = "Invalid json"
  val DB_ERROR = "DB error"
  val API_NOT_FOUND = "Api not found"

  case class ErrorMessage(json: JsValue)

  def generateError(errorMessage: String): ErrorMessage = {
    ErrorMessage(
      Json.obj(keyErrorMessage -> errorMessage)
    )
  }
}
