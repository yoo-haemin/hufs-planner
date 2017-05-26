package models.daos

import scala.concurrent.Future
import models.Course

trait CourseDAO {
  def findById(id: java.util.UUID): Future[Course]
}
