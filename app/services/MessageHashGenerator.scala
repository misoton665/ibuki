package services

import com.roundeights.hasher.Implicits._

object MessageHashGenerator {
  def generateHash(message: String, salt: String): String = {
    message.salt(salt).sha256
  }
}
