package controllers

import javax.inject.Inject
import java.time.Year

import com.mohiva.play.silhouette.api.{ Silhouette }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv

import models.services._
import models._

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class SummaryController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userCourseService: UserCourseService,
  courseService: CourseService,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Handles the summary action.
   *
   * @return The result to display.
   */
  def view = silhouette.SecuredAction.async { implicit request =>
    import scala.concurrent.ExecutionContext.Implicits.global
    for {
      allCourses <- userCourseService.allCourse(request.identity.userID)
      perSemesterAvg <- userCourseService.perSemesterAvg(request.identity.userID)
      perMajor <- userCourseService.perMajor(request.identity.userID)
    } yield {
      Ok(views.html.summary(perSemesterAvg, perMajor))
    }
  }
}
