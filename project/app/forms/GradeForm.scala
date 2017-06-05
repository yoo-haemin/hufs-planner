package forms

import play.api.data.Form
import play.api.data.Forms._

import shared.Regex._

/**
 * The form which handles the score input process.
 */
object GradeForm {

  /**
   * Form for accepting grade input
   */
  val form = Form(
    mapping(
      "main" -> text.verifying("wrong.input", data =>
        data match {
          case gradeAllR(body, point) => true
          case _ => false
        }
      )
    )(Data.apply)(Data.unapply _)
  )

  /**
   * The form data.
   *
   * @param firstName The first name of a user.
   * @param lastName The last name of a user.
   * @param email The email of the user.
   * @param password The password of the user.
   */
  case class Data(main: String)
}
