package services

import play.api.libs.json.{JsValue, Json}

object ErrorMessageGenerator {
  val keyErrorMessage = "errorMessage"
  val INVALID_JSON = "invalid json"

  case class ErrorMessage(json: JsValue)

  def generateError(errorMessage: String): ErrorMessage = {
    ErrorMessage(
      Json.obj(keyErrorMessage -> INVALID_JSON)
    )
  }
}
