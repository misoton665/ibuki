package services

import play.api.libs.json.JsObject

object ApplicationObject {
  trait Jsonable {
    def toJson: JsObject
  }

  trait JsonReadable[T] {
    def readJson(jsonString: String): Option[T]
  }
}
