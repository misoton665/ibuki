package services

import java.text.SimpleDateFormat
import java.util.Date

object DateConverter {
  val dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")

  def generateNowDateString(): String = {
    val date = new Date()
    dateFormat.format(date)
  }
}
