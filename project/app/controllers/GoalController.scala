package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Silhouette }
import play.api.i18n.{ I18nSupport, MessagesApi, Messages }
import play.api.mvc.Controller
import utils.auth.DefaultEnv
import models.services.UserCourseService
import models.services.CourseService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import shared.Util._
import forms.GoalForm

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param webJarAssets The webjar assets implementation.
 */
class GoalController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userCourseService: UserCourseService,
  courseService: CourseService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def view = silhouette.SecuredAction.async { implicit request =>
    for {
      recommendedCourse <- userCourseService.recommendedCourse(request.identity.userID, nextSemester)
      retakeCourse <- userCourseService.retakeCourse(request.identity.userID, nextSemester)
      otherMajorCourse <- userCourseService.otherMajorCourse(request.identity.userID, nextSemester)
    } yield {
      Ok(views.html.goal(request.identity, recommendedCourse, retakeCourse, otherMajorCourse))
    }
  }

  // TODO Finish me!
  def submit = silhouette.SecuredAction.async { implicit request =>
    GoalForm.form.bindFromRequest.fold(
      form => {
        Future.successful(
          Redirect(routes.GoalController.view()).flashing("error" -> form.get.toString)
        )
      },
      data => {
        import scala.concurrent.ExecutionContext.Implicits.global

        for {
          courseids <- reverseEffect(
            data.courseNo.map(
              d => {
                courseService.find(
                  year = Some(nextSemester._1),
                  semester = Some(nextSemester._2),
                  codePrefix = Some(d.take(6)),
                  codeSuffix = Some(d.drop(6)))
              })).map(s => {
              s.map(_.head.id)
            })

          savedCourse <- reverseEffect(courseids.map { courseid =>
            userCourseService.save(request.identity.userID, courseid, -1, future = true)
          })

        } yield Redirect(routes.SummaryController.view()).flashing("info" -> Messages("saved.future.course", savedCourse.length))
      }
    )
  }
}
