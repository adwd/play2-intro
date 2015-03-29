package controllers

// Play全体に関する機能、アプリケーション設定や
// ログ出力、ルーティングなどに関するクラス
import play.api._

// MVCの諸機能に関するクラス群
import play.api.mvc._

// Viewでのヘルパー等に関するクラス
// import play.api.views._

import play.api.data.Form
import play.api.data.Forms._

import play.api.db.slick._
import play.api.data.validation.Constraints.{pattern}
import java.sql.Timestamp

import views._
import models._

object Application extends Controller {

  val memberForm = Form(
		mapping(
  		"id" -> longNumber(min = 0, max = 1000),
  	  "name" -> nonEmptyText,
  	  "mail" -> nonEmptyText,
			"tel" -> nonEmptyText
	  )(Member.apply)(Member.unapply)
	)

	val messageForm = Form(
		mapping(
			"id" -> longNumber(min = 0, max = 1000),
			"name" -> nonEmptyText.verifying(pattern("""[0-9a-zA-Z]+""".r,
				error = "半角英数だけで入力してください。")),
			"message" -> nonEmptyText.verifying("メッセージエラー", msg => msg.length < 140),
			"postdate" -> ignored(new Timestamp(new java.util.Date().getTime()))
		)(Message.apply)(Message.unapply)
	)

  def index = DBAction { implicit rs =>
  	val messages = MessageDAO.all
		val members = MemberDAO.all
    Ok(html.index("データベースのサンプル", messages, members))
  }

  def addMessage = Action {
  	Ok(html.addmessage("投稿フォーム", messageForm))
  }

	def addMember = Action {
		Ok(html.addmember("メンバー登録フォーム", memberForm))
	}

  def createMessage = DBAction { implicit rs =>
  	messageForm.bindFromRequest.fold(
  		errors => BadRequest(html.addmessage(errors.errors.toString, errors)),
  		message => {
  			MessageDAO.create(message)
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
