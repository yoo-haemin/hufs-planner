package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form to accept user goals
 */
object GoalForm {

  /**
   * Form for accepting grade input
   */
  val form = Form(
    mapping(
      "courseNo" -> list(text)
    )(Data.apply)(Data.unapply _)
  )

  case class Data(courseNo: List[String])
}
