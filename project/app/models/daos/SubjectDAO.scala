package models.daos

import scala.concurrent.Future
import models.Subject

trait SubjectDAO {
  def findByDepartmentId(id: String): Future[Seq[Subject]]

  def findByCode(code: String): Future[Seq[Subject]]

  def findByName(name: String): Future[Seq[Subject]]

  def all(): Future[Seq[Subject]]

  def insert(Subject: Subject): Future[String]

}
