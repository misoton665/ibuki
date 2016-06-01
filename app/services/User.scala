package services

class User(id: String, name: String, email: Email) {

}

object User {
  def apply(id: String, name: String, email: Email): User = {
    new User(id, name, email)
  }
}

case class Email(email: String) {
  val isValidEmail: Boolean = email.matches("""^[0-9a-z_./?-]+@([0-9a-z-]+\.)+[0-9a-z-]+$""")
}

case class Member(id: String, name: String, email: Email, isOwner: Boolean = false) extends User(id, name, email){
  def toOwner: Member = Member(id, name, email, isOwner = true)
}

object Member {
  def apply(id: String, name: String, email: Email): Member = {
    new Member(id, name, email)
  }
}

case class Group(id: String, name: String, members: Array[Member], activities: Array[Activity]) {
  val owner: Option[Member] = members.filter(_.isOwner) match {
    case Array(x) => Some(x)
    case _ => None
  }
}
