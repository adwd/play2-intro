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

import views._

object Application extends Controller {

  case class SampleForm(message: String, name: String)

  // フォームのmessage, nameフィールドがtextフィールドであること、クラスの構築・分解メソッドを示す
  // play.api.data case class Form[T](mapping: Mapping[T], ~~
  val form1 = play.api.data.Form(
    mapping("message" -> play.api.data.Forms.text, "name" -> play.api.data.Forms.text)(SampleForm.apply)(SampleForm.unapply)
  )

  def index = Action {
    Ok(html.index("何か書いて", form1))
  }

  // bindFromRequestでrequestパラメーターを使用するため、implicit requestが必要
  def send = play.api.mvc.Action { implicit request =>
    /*
    def bindFromRequest()(implicit request: Request[_]): Form[T]
    Binds request data to this form, i.e. handles form submission.
    returns a copy of this form filled with the new data
    */
    var resform = form1.bindFromRequest // implicitでない場合は form1.bindFromRequest()(request)

    // res = request: POST /send, you typed: foo, your name: bar
    var res = s"request: $request, you typed: ${resform.get.message}, your name: ${resform.get.name}"
    Ok(html.index(res, resform))
  }
}
