package controllers

// Play全体に関する機能、アプリケーション設定や
// ログ出力、ルーティングなどに関するクラス
import play.api._

// MVCの諸機能に関するクラス群
import play.api.mvc._

// Viewでのヘルパー等に関するクラス
// import play.api.views._

import views._

object Application extends Controller {

  // アクションはResultを返す
  def index = Action {
    // Okはアクセスが正常に完了したResultを返す
    // index.scala.htmlテンプレートに引数を渡して描画する
    Ok(html.index("Your new application is ready."))
  }
}
