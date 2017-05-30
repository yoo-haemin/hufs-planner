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
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
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
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    classYear: Int,
    firstMajor: String,
    secondMajor: String,
    semester: Int
  )
}
