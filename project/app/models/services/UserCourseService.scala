package models.services

import java.util.UUID
import models.{ Course, UserCourse }
import scala.concurrent.Future

trait UserCourseService {
  /**
   * Retrieves courses of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of course to return
   * @return The retrieved courses
   */
  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[Course]]

  /**
   *
   */
  def save(userID: UUID, courseID: UUID, score: Int, retake: Boolean = false, future: Boolean = false): Future[UserCourse]
}
