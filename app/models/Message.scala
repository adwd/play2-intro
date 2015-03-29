package models

import java.sql.Timestamp

// Slick関連パッケージ
import play.api.db.slick.Config.driver.simple._

// DTOの定義
case class Message(id: Long, name: String, mail: String, message: String, postdate: Timestamp)

// この形式で記述することで、CREATE TABLE 文と DROP TABLE 文を自動的に生成します。
class MessageTable(tag: Tag) extends Table[Message](tag, "MESSAGES") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME", O.NotNull)
  def mail = column[String]("MAIL", O.NotNull)
  def message = column[String]("MESSAGE", O.NotNull)
  def postdate = column[Timestamp]("POSTDATE", O.NotNull)

  def * = (id, name, mail, message, postdate) <> (Message.tupled, Message.unapply)
}

// DAOの定義
object MessageDAO {
  lazy val messageQuery = TableQuery[MessageTable]

  // ID検索
  def searchByID(id: Long)(implicit s: Session): Option[Message] = {
    messageQuery.filter(_.id === id).firstOption
  }

  // 検索
  def search(word: String)(implicit s: Session): List[Message] = {
    messageQuery.filter(row => (row.name like "%"+word+"%") || (row.mail like "%"+word+"%"))
      .sortBy(_.name.desc)
      .list
  }

  // 作成
  def create(message: Message)(implicit s: Session) {
    messageQuery.insert(message)
  }

  // 全件表示
  def all(implicit s: Session): List[Message] = messageQuery.list

  // 更新
  def update(message: Message)(implicit s: Session): Unit = {
    messageQuery.filter(_.id === message.id).update(message)
  }

  def delete(message: Message)(implicit s: Session) = {
    messageQuery.filter(_.id === message.id).delete
  }
}
