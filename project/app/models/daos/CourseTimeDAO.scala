package models.daos

import scala.concurrent.Future
import models.CourseTime

trait CourseTimeDAO {
  def findById(courseId: Int): Future[Option[CourseTime]]

  def all(): Future[Seq[CourseTime]]

  def insert(major: CourseTime): Future[Int]

}
