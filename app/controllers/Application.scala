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

import views._
import models._

object Application extends Controller {

  val messageForm = Form(
		mapping(
  		"id" -> longNumber(min = 0, max = 1000),
  	  "name" -> nonEmptyText.verifying(pattern("""[0-9a-zA-Z]+""".r,
				error = "半角英数だけで入力してください。")),
  	  "mail" -> email,
  	  "message" -> nonEmptyText.verifying(msg => msg == "message" && msg.length < 140)
	  )(Message.apply)(Message.unapply)
	)

	case class FindForm(input: String)

	val findForm = Form(
		mapping(
			"input" -> nonEmptyText(maxLength = 30)
		)(FindForm.apply)(FindForm.unapply)
	)

  def index = DBAction { implicit rs =>
  	val messages = MessageDAO.all
    Ok(html.index("データベースのサンプル", messages))
  }

  def add = Action {
  	Ok(html.add("投稿フォーム", messageForm))
  }

  def create = DBAction { implicit rs =>
  	messageForm.bindFromRequest.fold(
  		errors => BadRequest(html.add(errors.errors.toString, errors)),
  		message => {
  			MessageDAO.create(message)
  			Redirect(routes.Application.index)
      }
    )
  }

	// 編集するアイテムのIDを入力する画面を表示する
	def setitem = Action {
		Ok(html.item("ID番号を入力", messageForm))
	}

	// 指定されたIDのアイテムの編集画面を表示する
	def edit = DBAction { implicit rs =>
		messageForm.bindFromRequest.fold(
			errors => BadRequest(html.item("ERROR: 入力に問題があります" + errors.errors.toString, errors)),
			message => {
				// idを検索してあったら編集のページヘ移動、無かったらエラー表示
				MessageDAO.searchByID(message.id) match {
					case Some(m) => Ok(html.edit(s"", messageForm.fill(m)))
					case None => Ok(html.item("ERROR: IDの投稿が見つかりません", messageForm.fill(message)))
				}
			}
		)
	}

	// フォームに入力された内容で更新する
	def update = DBAction { implicit re =>
		messageForm.bindFromRequest.fold(
			error => BadRequest(html.edit("ERROR: 再度入力ください " + error.errors.toString, error)),
			message => {
					MessageDAO.update(message)
					Redirect(routes.Application.index)
			}
		)
	}

	// 削除するアイテムのIDを入力する画面を表示
	def delete = Action {
		Ok(html.delete("削除するID番号を入力", messageForm))
	}

	def remove = DBAction { implicit rs =>
		messageForm.bindFromRequest.fold(
			error => BadRequest(html.edit("ERROR: 入力にエラーが起こりました " + error.errors.toString, error)),
			message => {
				// idを検索してあったら削除、無かったらエラー表示
				MessageDAO.searchByID(message.id) match {
					case Some(m) => {
						MessageDAO.delete(m)
						Redirect(routes.Application.index)
					}
					case None => Ok(html.item("ERROR: IDの投稿が見つかりません", messageForm.fill(message)))
				}
			}
		)
	}

	def find = DBAction { implicit rs =>
		val find = findForm.bindFromRequest.fold(
			error => Nil,
			success => MessageDAO.search(success.input)
		)

		Ok(html.find("投稿の編集", findForm.bindFromRequest, find))
	}

}
