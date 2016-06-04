import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json
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

    Json.parse(jsonString).asOpt[User].isDefined should be(true)
  }

  "An Action JSON" should "be read valid Action" in {
    val jsonString =
      """
        |{
        |  "contributor_id": "test",
        |  "action_type": "document",
        |  "tags": ["tag1", "tag2"],
        |  "body": "This is a test."
        |}
      """.stripMargin

    Json.parse(jsonString).asOpt[Action].isDefined should be(true)
  }

  "An Activity JSON" should "be read valid Activity" in {
    val jsonString =
      """
        |{
        |  "actions": [
        |  {
        |    "contributor_id": "test",
        |    "action_type": "root",
        |    "tags": ["root", "tag1", "tag2"],
        |    "body": "This is a test."
        |  },
        |  {
        |    "contributor_id": "test",
        |    "action_type": "document",
        |    "tags": ["tag1", "tag2"],
        |    "body": "This is a test."
        |  }
        |  ]
        |}
      """.stripMargin

    Activity.readJson(jsonString).isDefined should be(true)
  }

}
