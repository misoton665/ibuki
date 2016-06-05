package services

import play.api.libs.json.JsObject

object ApplicationObject {
  trait Jsonable {
    def toJson: JsObject
  }
}
