package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import services.ResultMessage

class SearchController @Inject() extends Controller {

  def search(target: String) = Action {

    val result = target match {
      case "activity" => searchActivity()
      case _ => ResultMessage.generateError(ResultMessage.MESSAGE_API_NOT_FOUND).json.toString()
    }

    Ok(result)
  }

  def searchActivity(): String = {
    "activity"
  }

  def searchAction(): String = {
    "action"
  }

  def searchUser(): String = {
    "user"
  }

  def searchGroup(): String = {
    "group"
  }
}
