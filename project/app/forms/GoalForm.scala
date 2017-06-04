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
    seq(
      mapping(
        "courseNo" -> (text verifying (t => t.length > 0 && t.length < 12))
      )(Data.apply)(Data.unapply _)
    )
  )

  case class Data(courseid: String)
}
