package controllers

// Play全体に関する機能、アプリケーション設定や
// ログ出力、ルーティングなどに関するクラス
import play.api._
import play.api.data.Form
import play.api.data.Forms._

// MVCの諸機能に関するクラス群
import play.api.mvc._

// Viewでのヘルパー等に関するクラス
// import play.api.views._

import play.api.db.slick._
import play.api.data.validation.Constraints.{pattern}
import java.sql.Timestamp

import views._
import models._

object Application extends Controller {
  val memberForm = Form(
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "mail" -> nonEmptyText,
      "tel" -> nonEmptyText
    )(Member.apply)(Member.unapply)
  )

  val messageForm = Form(
    mapping(
      "id" -> longNumber,
      "memberid" -> ignored(0L),
      "message" -> nonEmptyText,
      "postdate" -> ignored(new Timestamp(new java.util.Date().getTime()))
    )(Message.apply)(Message.unapply)
  )

  def index = DBAction { implicit rs =>
		Ok(html.index("messages", MessageDAO.all, MemberDAO.all))
  }

  def addMessage = DBAction { implicit rs =>
    Ok(html.addmessage("投稿フォーム", messageForm, MemberDAO.all))
  }

  def addMember = Action {
    Ok(html.addmember("メンバー登録フォーム", memberForm))
  }

  def createMessage = DBAction { implicit rs =>
    messageForm.bindFromRequest.fold(
      errors => BadRequest(html.addmessage(errors.errors.toString, errors, MemberDAO.all)),
      message => {
        val name: String = messageForm.bindFromRequest.apply("name").value.get
        val createdmessage = message.copy(memberid = MemberDAO.search(name).apply(0).id)
        MessageDAO.create(createdmessage)
        Redirect(routes.Application.index)
      }
    )
  }

  def createMember = DBAction { implicit rs =>
    memberForm.bindFromRequest.fold(
      errors => BadRequest(html.addmember(errors.errors.toString, errors)),
      member => {
        MemberDAO.create(member)
        Redirect(routes.Application.index)
      }
    )
  }
}
