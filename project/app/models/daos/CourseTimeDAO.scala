package models.daos

import java.util.UUID
import scala.concurrent.Future
import models.CourseTime

trait CourseTimeDAO {
  def findById(courseId: UUID): Future[Option[CourseTime]]

  def all(): Future[Seq[CourseTime]]

  def insert(major: CourseTime): Future[UUID]

}
