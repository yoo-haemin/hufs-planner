package models.services

import java.util.UUID
import models._
import java.time.Year

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

  def find(year: Year, semester: Semester, codePrefix: String, department: Option[Department]): Future[Option[Course]]

  def find(year: Option[Year] = None, semester: Option[Semester] = None, codePrefix: Option[String] = None, codeSuffix: Option[String] = None): Future[Seq[Course]]

  def findAllById(ids: Seq[UUID]): Future[Seq[Option[Course]]]

  def findBySubject(subject: Subject): Future[Seq[Course]]

}
