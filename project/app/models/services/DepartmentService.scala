package models.services

import models.{ Department }

import scala.concurrent.Future

trait DepartmentService {
  def findById(id: String): Future[Department]

  def findByName(name: String): Future[Option[Department]]

  def all(): Future[Seq[Department]]

  def insert(Department: Department): Future[String]
}
