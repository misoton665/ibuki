import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsValue, Json}
import services.ActionTag.AttributeTag
import services.Contribution.{Action, Activity, DocumentAction, RootAction}
import services.DateConverter
import services.Organization.{Email, User}

class ServiceSpec extends FlatSpec with Matchers {

  val emailModel: Email = Email("misoton998@ibuki.com")

  val userModel: User = User("model_id", "model_name", emailModel, None)

  val actionTagModel_test: AttributeTag = AttributeTag("test tag")

  val bodyModel_test: String = "test body"

  val date: String = DateConverter.generateNowDateString()

  val rootActionModel: RootAction = RootAction(userModel, Array(), bodyModel_test + ": root", Some(date))

  val documentActionModel: DocumentAction = DocumentAction(userModel, Array(actionTagModel_test), bodyModel_test + ": document", Some(date))

  val activityModel: Activity = Activity(rootActionModel)

  "A head of Activity" should "be RootAction" in {
    activityModel.rootAction should be(rootActionModel)
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

  "An User" should "be corresponding to its JsValue" in {
    val user: JsValue = Json.toJson(userModel)

    user.asOpt[User].isDefined should be(true)
  }

  "Action" should "be read valid Action" in {
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

  "An Action" should "be corresponding to its JsValue" in {
    val action = Json.toJson(documentActionModel)

    action.asOpt[Action].isDefined should be(true)
  }

  "An Activity JSON" should "be read valid Activity" in {
    val jsonString =
      """
        |{
        |  "root_action":
        |  {
        |    "contributor_id": "test",
        |    "action_type": "root",
        |    "tags": ["root", "tag1", "tag2"],
        |    "body": "This is a test."
        |  }
        |}
      """.stripMargin

    Json.parse(jsonString).asOpt[Activity].isDefined should be(true)
  }

  "An Activity" should "be corresponding to its JsValue" in {
    val activity = Json.toJson(activityModel)

    activity.asOpt[Activity].isDefined should be(true)
  }

}
