package models.services

import java.util.UUID
import models._

import scala.concurrent.Future

trait CourseService {

  /**
   * Returns a matching Major with a matching name
   *
   * @param name The name of the Major, in Korean
   * @param majorType The type of the Major
   * @return The found major.
   */
  def findById(id: UUID): Future[Option[Course]]

  def findAllById(ids: Seq[UUID]): Future[Seq[Option[Course]]]

  def findBySubject(subject: Subject): Future[Seq[Course]]
}
