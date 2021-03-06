package models.daos

import java.util.UUID

import models.{ User, Course, UserCourse }

import scala.concurrent.Future

/**
 * Give access to the user object.
 */
trait UserCourseDAO {

  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[UserCourse]]

  def save(userCourse: UserCourse): Future[UserCourse]

  def remove(userCourse: UserCourse): Future[Unit]
}
