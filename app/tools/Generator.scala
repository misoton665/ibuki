package tools

import scala.slick.codegen.SourceCodeGenerator

object Generator {
  def main(args: Array[String]): Unit = {
    println("Start")
    val slickDriver = "scala.slick.driver.PostgresDriver"
    val jdbcDriver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost/playdb"
    val outputFolder = "app/"
    val pkg = "modules"

    SourceCodeGenerator.main {
      Array(
        slickDriver,
        jdbcDriver,
        url,
        outputFolder,
        pkg
      )
    }

    println("end")
  }
}
