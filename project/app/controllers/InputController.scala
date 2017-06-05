package controllers

import javax.inject.Inject
import java.time.Year

import com.mohiva.play.silhouette.api.{ Silhouette }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.mvc.Controller
import utils.auth.DefaultEnv
import forms.GradeForm
import shared.Regex._

import scala.concurrent.Future
import models.Semester._
import models._
import models.Score._
import models.services._
import shared.Util.reverseEffect

/**
 * The input controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param socialProviderRegistry The social provider registry.
 * @param webJarAssets The webjar assets implementation.
 */
class InputController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  socialProviderRegistry: SocialProviderRegistry,
  departmentService: DepartmentService,
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
    Future.successful(Ok(views.html.input(request.identity)(GradeForm.form)))
  }

  def submit = silhouette.SecuredAction.async { implicit request =>
    GradeForm.form.bindFromRequest.fold(
      form => {
        Future.successful(
          Redirect(routes.InputController.view()).flashing("error" -> Messages("grades.wrong.input"))
        )
      },
      data => {
        import scala.concurrent.ExecutionContext.Implicits.global

        val semesters = semesterR.findAllMatchIn(data.main).toArray
        val total = data.main match {
          case totR(tot) => tot.toDouble.toInt
          case _ => null
        }

        reverseEffect(
          (for {
          semester <- semesters
          grade <- gradeR.findAllMatchIn(semester.toString)
        } yield {
          val yr = Year.of(semester.group(2).toInt)
          val sem = if (grade.group(9) == "여름") SummerSemester
          else if (grade.group(9) == "겨울") WinterSemester
          else if (semester.group(3) == "1") FirstSemester
          else if (semester.group(3) == "2") SecondSemester
          else throw new NoSuchElementException(s"Inappropriate Semester in Form, details: ${semester.group(2)}, ${grade.group(9)}")

          val deptFut = departmentService.findByName(grade.group(1))
          val codePrefix = grade.group(2)
          val subjectName1 = grade.group(3)
          val subjectName2 = grade.group(4)
          val majorType = grade.group(5)
          val creditHours = grade.group(6).toInt
          val courseGrade = grade.group(7)
          val retake = if (grade.group(8) == "재") true else false

          for {
            dept <- deptFut
            courseOpt <- courseService.find(yr, sem, codePrefix, dept)
          } yield (courseOpt, courseGrade, retake)
        }
        ).toSeq).flatMap { courseOpts =>
          val wrongInput = courseOpts.filter(_._1.isEmpty)
          if (wrongInput.size > 0)
            Future.successful(Redirect(routes.InputController.view()).flashing("error" -> Messages("grades.wrong.input"))) //Has at least one not understood row
          else if (total != courseOpts.map(_._1.get.creditHours).sum)
            Future.successful(Redirect(routes.InputController.view()).flashing("error" -> Messages("grades.wrong.input.total.mismatch"))) //Has at least one not understood row
          else
            userCourseService.removeAll(request.identity.userID).flatMap { _ =>
              reverseEffect(
                courseOpts
                  .map {
                    case (cOpt, score, retake) =>
                      userCourseService.save(
                        request.identity.userID, cOpt.get.id, Score.fromString(score).toInt, retake = retake)
                  }
              )
            }
              .map { seq => //TODO find a way to utilize the seq data?
                Redirect(routes.SummaryController.view()).flashing("success" -> Messages("grades.process.success"))
              }
        }
      }
    )
  }
}
