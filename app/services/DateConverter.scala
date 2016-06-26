package services

import java.text.SimpleDateFormat
import java.sql.Date

object DateConverter {
  val dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")

  def generateNowDateString(): String = {
    val date = new Date(System.currentTimeMillis())
    dateFormat.format(date)
  }

  val generateNowDate: Date = new Date(System.currentTimeMillis())
}
