import org.scalatest.{FlatSpec, Matchers}
import services.{Activity, Email, Member, RootAction}

class ServiceSpec extends FlatSpec with Matchers {

  val emailModel: Email = Email("misoton998@ibuki.com")

  val memberModel: Member = Member("model_id", "model_name", emailModel)

  val rootActionModel: RootAction = RootAction(memberModel, Array())

  val activityModel: Activity = Activity(Array(rootActionModel))

  "A head of Activity" should "be RootAction" in {
    activityModel.rootAction should be(Some(rootActionModel))
  }

}
