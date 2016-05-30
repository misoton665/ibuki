package services

case class User(id: String, name: String)

class Member(id: String, name: String) {
  val isOwner: Boolean = false
}

object Member {
  def apply(id: String, name: String): Member = {
    new Member(id, name)
  }
}

case class Owner(id: String, name: String) extends Member(id, name){
  override val isOwner = true
}