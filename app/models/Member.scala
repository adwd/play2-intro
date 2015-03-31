package models

import play.api.db.slick.Config.driver.simple._

case class Member(id: Long, name: String, mail: String, tel: String)

class MemberTable(tag: Tag) extends Table[Member](tag, "MEMBERS") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME", O.NotNull)
  def mail = column[String]("MAIL", O.NotNull)
  def tel = column[String]("MESSAGE", O.NotNull)

  def * = (id, name, mail, tel) <> (Member.tupled, Member.unapply)
}

object MemberDAO {
  lazy val memberQuery = TableQuery[MemberTable]

  def searchByID(id: Long)(implicit s: Session): Option[Member] = {
    memberQuery.filter(_.id === id).firstOption
  }

  def search(word: String)(implicit s: Session): List[Member] = {
    memberQuery.filter(row => row.name like "%"+word+"%")
      .sortBy(_.name.desc)
      .list
  }

  def create(member: Member)(implicit s: Session) {
    memberQuery.insert(member)
  }

  def all(implicit s: Session): List[Member] = memberQuery.list

  def update(member: Member)(implicit s: Session): Unit = {
    memberQuery.filter(_.id === member.id).update(member)
  }

  def delete(member: Member)(implicit s: Session) = {
    memberQuery.filter(_.id === member.id).delete
  }
}