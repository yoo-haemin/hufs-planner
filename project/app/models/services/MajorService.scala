package models.services

import java.util.UUID
import models.{ Major, MajorType, Subject, Course }

import scala.concurrent.Future

/**
 * Handles actions to auth tokens.
 */
trait MajorService {

  /**
   * Returns a matching Major with a matching name
   *
   * @param name The name of the Major, in Korean
   * @param majorType The type of the Major
   * @return The found major.
   */
  def find(name: String, majorType: MajorType): Future[Option[Major]]

  def find(name: String, majorType: MajorType, year: Short): Future[Option[Major]]

  /**
   * Returns a sequence of majors of a given user
   *  @param subject The user to search
   *  @return The sequence majors the user has
   */
  def findByUser(userID: UUID): Future[Seq[Major]]

  /**
   * Returns a sequence of majors and the given subject's requiredness
   *  @param subject The subject to search
   *  @return The sequence of pairs of matching majors and its requiredness
   */
  def findBySubject(subject: Subject): Future[Seq[(Major, Boolean)]]

  /**
   * Returns a sequence of majors and the subject's requiredness of a given course
   *  @param course The course to search
   *  @return The sequence of pairs of matching majors and its requiredness
   */
  def findByCourse(course: Course): Future[Seq[(Major, Boolean)]]

  def allOfType(): Future[Map[MajorType, Seq[(String, String)]]]
}
