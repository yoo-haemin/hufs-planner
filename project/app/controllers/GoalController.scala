package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{ Silhouette }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv

import scala.concurrent.Future

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
  socialProviderRegistry: SocialProviderRegistry,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def view = silhouette.UnsecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.goal()))
  }

  /* TODO 윗 버전은 관상용, 이 버전으로 바꾸기!!
  def view = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.input(request.identity)))
  }
   */

  // TODO Finish me!
  def submit = silhouette.UnsecuredAction.async { implicit request =>
    Future.successful(Ok("OK"))
  }
}
