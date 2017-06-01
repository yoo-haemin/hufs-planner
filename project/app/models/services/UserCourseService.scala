package models.services

import java.util.UUID
import models.{ Course, UserCourse }
import scala.concurrent.Future

trait UserCourseService {
  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[Course]]

  def save(userID: UUID, courseID: UUID, future: Boolean = false): Future[UserCourse]

  def saveAll(userID: UUID, courseIDs: Seq[UUID], future: Boolean): Future[Seq[UserCourse]]

}
