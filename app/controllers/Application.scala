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
  def index = Action { request =>
    request.method match {
      case "POST" =>
        val msg = request.body.asFormUrlEncoded.get("input")(0)
        Ok(html.index(s"posted: $msg"))
      case _ => Ok(html.index("please type"))
    }
  }
}
