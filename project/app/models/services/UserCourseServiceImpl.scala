package models.services

import java.util.UUID
import javax.inject.Inject

import models.{ User, Course, UserCourse }
import models.daos.UserCourseDAO
import models.daos.CourseDAO
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class UserCourseServiceImpl @Inject() (userCourseDAO: UserCourseDAO) extends UserCourseService {

  /**
   * Retrieves courses of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of course to return
   * @return The retrieved courses
   */
  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[Course]] =
    userCourseDAO.allCourse(userID, future)

  def save(userID: UUID, courseID: UUID, score: Int,
    retake: Boolean = false, future: Boolean = false): Future[UserCourse] =
    userCourseDAO.save(UserCourse(userID, courseID, score, retake, future))
}
