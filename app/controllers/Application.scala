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

import java.util.Date

import views._
import models._

object Application extends Controller {
	case class SampleForm(
												 input: String,
												 pass: String,
												 check: Boolean,
												 radio: String,
												 sel: String,
												 area: String,
												 date: Date)

	val sampleform = Form(
		mapping(
			"input" -> text,
			"pass"  -> text,
			"check" -> boolean,
			"radio" -> text,
			"sel"   -> text,
			"area"  -> text,
			"date"  -> date
		)(SampleForm.apply)(SampleForm.unapply)
	)

  def index = Action {
		val form = sampleform.fill(SampleForm("default value", "", true, "windows", "uk", "", null))
		Ok(html.index("please fill form", form))
  }

	def send = Action { implicit rs =>
		sampleform.bindFromRequest().fold(
			error => BadRequest(html.index("error", error)),
			success => Ok(html.index(success.toString, sampleform.fill(success)))
		)
	}
}
