package controllers

// Play�S�̂Ɋւ���@�\�A�A�v���P�[�V�����ݒ��
// ���O�o�́A���[�e�B���O�ȂǂɊւ���N���X
import play.api._

// MVC�̏��@�\�Ɋւ���N���X�Q
import play.api.mvc._

// View�ł̃w���p�[���Ɋւ���N���X
// import play.api.views._

import views._

object Application extends Controller {

  // �A�N�V������Result��Ԃ�
  def index = Action {
    // Ok�̓A�N�Z�X������Ɋ�������Result��Ԃ�
    // index.scala.html�e���v���[�g�Ɉ�����n���ĕ`�悷��
    Ok(html.index("Your new application is ready."))
  }
}
