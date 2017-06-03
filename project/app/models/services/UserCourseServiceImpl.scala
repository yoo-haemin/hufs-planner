package models.services

import java.util.UUID
import java.time.Year
import javax.inject.Inject

import models.{ Course, UserCourse, Score, Semester, Major }
import models.daos.UserCourseDAO
import shared.Util.reverseEffect
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class UserCourseServiceImpl @Inject() (
  userCourseDAO: UserCourseDAO,
  courseService: CourseService,
  majorService: MajorService,
  userMajorService: UserMajorService
) extends UserCourseService {

  /**
   * Retrieves courses of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of course to return
   * @return The retrieved courses
   */
  def allUserCourse(userID: UUID, future: Boolean = false): Future[Seq[UserCourse]] =
    userCourseDAO.allCourse(userID, future)

  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[(Option[Course], Score)]] =
    for {
      userCourses <- allUserCourse(userID)
      userCourseInfo <- courseService.findAllById(userCourses.map(_.courseID))
      scores = userCourses.map(_.score)
    } yield userCourseInfo zip scores

  def perSemester(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), Seq[(Option[Course], Score)]]] =
    allCourse(userID, future).map { ac =>
      ac
        .groupBy {
          case (Some(c), s) => (c.year -> c.semester)
          case (None, _) => throw new NoSuchElementException("User registered an unknown course")
        }
    }

  def perSemesterAvg(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), (Int, Double)]] =
    perSemester(userID, future).map { ac =>
      ac
        .map {
          case ((yr, sem), seq) =>
            val credits = seq.map(_._1.get.creditHours.toInt)

            (yr -> sem) -> (credits.sum -> Score.avg(seq.map(_._2) zip credits))
        }
    }

  def perMajor(userID: UUID, future: Boolean = false): Future[Map[Major, Seq[(Option[Course], Score)]]] =
    for {
      allCourses <- allCourse(userID, future) //all courses user took
      userMajor <- majorService.findByUser(userID) //all major user has
      allCoursesWithMajor <- reverseEffect(
        allCourses.map {
          case (Some(course), score) =>
            (majorService.findByCourse(course) zip
              (Future.successful(Some(course)) zip Future.successful(score)))
          case (None, _) => throw new NoSuchElementException("User registered an unknown course")
        })
      userMajorCourse = allCoursesWithMajor.map { allCwM =>
        val majorPair: Seq[(Major, Boolean)] = allCwM._1
        val majors: Seq[Major] = majorPair.map(_._1)
        majors.toSet.intersect(userMajor.toSet).head -> allCwM._2
      }
    } yield userMajorCourse
      .groupBy(_._1)
      .map {
        case (maj, seq) =>
          maj -> seq.map {
            case (maj, (cOpt, score)) =>
              cOpt -> score
          }
      }.toMap

  def perMajorAvg(userID: UUID, future: Boolean = false): Future[Map[Major, (Int, Double)]] =
    perMajor(userID, future).map { ac =>
      ac
        .map {
          case (major, seq) =>
            val credits = seq.map(_._1.get.creditHours.toInt)

            major -> (credits.sum -> Score.avg(seq.map(_._2) zip credits))
        }
    }

  def save(userID: UUID, courseID: UUID, score: Int, retake: Boolean = false, future: Boolean = false): Future[UserCourse] =
    userCourseDAO.save(UserCourse(userID, courseID, score, retake, future))
}
