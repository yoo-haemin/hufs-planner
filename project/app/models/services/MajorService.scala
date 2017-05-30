package models.services

import models.{ Major, MajorType }

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

  def allOfType(): Future[Map[MajorType, Seq[(String, String)]]]
}
