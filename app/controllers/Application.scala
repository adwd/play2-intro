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

import views._
import models._

object Application extends Controller {

  val messageForm = Form(
  	  mapping(
  	  	"id" -> longNumber,
  	  	"name" -> nonEmptyText(maxLength = 30),
  	  	"mail" -> nonEmptyText(maxLength = 30),
  	  	"message" -> nonEmptyText(maxLength = 140)
	  )(Message.apply)(Message.unapply)
  	)

  def index = DBAction { implicit rs =>
  	val messages = MessageDAO.all
    Ok(html.index("データベースのサンプル", messages))
  }

  def add = Action {
  	Ok(views.html.add("投稿フォーム", messageForm))
  }

  def create = DBAction { implicit rs =>
  	messageForm.bindFromRequest.fold(
  		errors => BadRequest(views.html.add(errors.errors.toString, errors)),
  		message => {
  			MessageDAO.create(message)
  			Redirect(routes.Application.index)
      }
    )
  }
}
