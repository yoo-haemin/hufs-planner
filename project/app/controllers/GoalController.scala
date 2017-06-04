package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Silhouette }
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv
import models.services.UserCourseService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import shared.Util.nextSemester

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class GoalController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userCourseService: UserCourseService,
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
      println("recom" + recommendedCourse)
      println("retak" + retakeCourse)
      println("other" + otherMajorCourse)

      Ok(views.html.goal(request.identity, recommendedCourse, retakeCourse, otherMajorCourse))
    }
  }

  // TODO Finish me!
  def submit = silhouette.UnsecuredAction.async { implicit request =>
    Future.successful(Ok("OK"))
  }
}
