package models

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}
import java.sql.Date

// Message Model class
class Messages(tag: Tag)
  extends Table[(Int, String, String, String, Date)](tag, "MESSAGES") {

  // This is the primary key column:
  def id: Column[Int] = column[Int]("ID", O.PrimaryKey)
  def name: Column[String] = column[String]("NAME")
  def mail: Column[String] = column[String]("MAIL")
  def message: Column[String] = column[String]("MESSAGE")
  def postdate: Column[Date] = column[Date]("POSTDATE")
  
  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, String, Date)] =
    (id, name, mail, message, postdate)
}
