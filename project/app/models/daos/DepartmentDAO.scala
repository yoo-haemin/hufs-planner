package models.daos

import models.{ Department }

import scala.concurrent.Future

/**
 * Give access to the [[Department]] object.
 */
trait DepartmentDAO {

  /**
   *
   */
  def findById(id: String): Future[Department]

  def findByName(name: String): Future[Option[Department]]

  def all(): Future[Seq[Department]]

  def insert(department: Department): Future[String]

}
