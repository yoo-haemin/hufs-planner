package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the sign up process.
 */
object SignUpForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "email" -> email,
      "schoolNumber" -> nonEmptyText,
      "password" -> nonEmptyText,
      "classYear" -> number,
      "firstMajor" -> nonEmptyText,
      "secondMajor" -> text,
      "semester" -> number
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
  case class Data(
    email: String,
    schoolNumber: String,
    password: String,
    classYear: Int,
    firstMajor: String,
    secondMajor: String,
    semester: Int
  )
}
