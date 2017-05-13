package models.daos

import scala.concurrent.Future
import fun.lambda.coursecrawler.Course

trait CourseDAO {

  def findById(id: Long): Future[Course]

  def findByColor(color: String): Future[Option[Course]]

  def findByProjectId(projectId: Long): Future[List[Course]]

  def all(): Future[Seq[Course]]

  def insert(course: Course): Future[Long]
}
