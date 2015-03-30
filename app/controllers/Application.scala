package controllers

// Play全体に関する機能、アプリケーション設定や
// ログ出力、ルーティングなどに関するクラス
import play.api._

// MVCの諸機能に関するクラス群
import play.api.mvc._

// Viewでのヘルパー等に関するクラス
// import play.api.views._

import views._
import models._

object Application extends Controller {
  def index = Action { implicit request =>
    val message = request.getQueryString("msg") match {
      case Some(x) => s"引数は${x}です"
      case _ => "no query string"
    }
    Ok(html.index(message))
  }
}
