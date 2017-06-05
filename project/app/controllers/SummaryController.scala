package controllers

import javax.inject.Inject
import java.time.Year

import com.mohiva.play.silhouette.api.{ Silhouette }
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
      perSemesterAvg <- userCourseService.perSemesterAvg(request.identity.userID)
      perSemesterAvgFut <- userCourseService.perSemesterAvg(request.identity.userID, true)
      perSemesterAvgTotal = perSemesterAvg ++ perSemesterAvgFut

      perMajor <- userCourseService.perMajor(request.identity.userID, future = false)
      perMajorFut <- userCourseService.perMajor(request.identity.userID, future = true)
    } yield {
      Ok(views.html.summary(perSemesterAvgTotal, perMajor, perMajorFut, request.identity))
    }
  }
}
