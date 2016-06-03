import org.scalatest.{FlatSpec, Matchers}
import services.ActionTag.ActionTag
import services.Contribution.{Action, Activity, DocumentAction, RootAction}
import services.Organization.{Email, User}

class ServiceSpec extends FlatSpec with Matchers {

  val emailModel: Email = Email("misoton998@ibuki.com")

  val memberModel: User = User("model_id", "model_name", emailModel)

  val actionTagModel_test: ActionTag = ActionTag("test tag")

  val bodyModel_test: String = "test body"

  val rootActionModel: RootAction = RootAction(memberModel, Array(), bodyModel_test + ": root")

  val documentActionModel: DocumentAction = DocumentAction(memberModel, Array(actionTagModel_test), bodyModel_test + ": document")

  val activityModel: Activity = Activity(Array(rootActionModel))

  "A head of Activity" should "be RootAction" in {
    activityModel.rootAction should be(Some(rootActionModel))
  }

  "A User JSON" should "be read valid User" in {
    val jsonString =
      """
        |{
        |  "user_id": "test",
        |  "user_name": "poe",
        |  "email": "misoton998@ibuki.com"
        |}
      """.stripMargin

    User.readJson(jsonString).isDefined should be(true)
  }

  "A Action JSON" should "be read valid Action" in {
    val jsonString =
      """
        |{
        |  "contributor_id": "test",
        |  "actionType": "document",
        |  "tags": ["tag1", "tag2"],
        |  "body": "This is a test."
        |}
      """.stripMargin

    Action.readJson(jsonString).isDefined should be(true)

    val email = Action.readJson(jsonString) match {
      case Some(action) => action.contributor.email.address
      case None => "invalid action"
    }

    email should be("test@test.com")
  }

}
