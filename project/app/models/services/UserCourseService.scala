package models.services

import java.util.UUID
import java.time.Year

import models.{ UserCourse, Course, Score, Major, Semester }
import scala.concurrent.Future

trait UserCourseService {

  /**
   * Retrieves courses of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of course to return
   * @return The retrieved courses
   */
  def allUserCourse(userID: UUID, future: Boolean = false): Future[Seq[UserCourse]]

  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[(Option[Course], Score)]]

  def perSemester(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), Seq[(Option[Course], Score)]]]

  def perSemesterAvg(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), (Int, Double)]]

  def perMajor(userID: UUID, future: Boolean = false): Future[Map[Major, Seq[(Option[Course], Score)]]]

  def perMajorAvg(userID: UUID, future: Boolean = false): Future[Map[Major, (Int, Double)]]

  /**
   *
   */
  def save(userID: UUID, courseID: UUID, score: Int, retake: Boolean = false, future: Boolean = false): Future[UserCourse]
}
