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
  def index = DBAction { implicit rs =>
  	val messages = MessageDAO.showAll
    Ok(html.index("データベースのサンプル", messages))
  }
}
