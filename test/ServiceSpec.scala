import org.scalatest.{FlatSpec, Matchers}
import services.ActionTag.ActionTag
import services.Activity.{Activity, DocumentAction, RootAction}
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

}
