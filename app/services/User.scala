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

class Member(id: String, name: String, email: Email) extends User(id, name, email){
  val isOwner: Boolean = false
}

object Member {
  def apply(id: String, name: String, email: Email): Member = {
    new Member(id, name, email)
  }
}

case class Owner(id: String, name: String, email: Email) extends Member(id, name, email) {
  override val isOwner = true
}