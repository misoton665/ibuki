import org.scalatest.{FlatSpec, Matchers}
import services._

class ServiceSpec extends FlatSpec with Matchers {

  val emailModel: Email = Email("misoton998@ibuki.com")

  val memberModel: Member = Member("model_id", "model_name", emailModel)

  val actionTagModel_test: ActionTag = AttributeTag("test")

  val rootActionModel: RootAction = RootAction(memberModel, Array())

  val documentActionModel: DocumentAction = DocumentAction(memberModel, Array(actionTagModel_test))

  val activityModel: Activity = Activity(Array(rootActionModel))

  "A head of Activity" should "be RootAction" in {
    activityModel.rootAction should be(Some(rootActionModel))
  }

}
