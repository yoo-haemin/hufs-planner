package models.daos

import scala.concurrent.Future
import java.util.UUID
import models.Subject

trait SubjectDAO {
  def findByDepartmentId(id: String): Future[Subject]

  def findByName(name: String): Future[Subject]

  def all(): Future[Seq[Subject]]

  def insert(Subject: Subject): Future[UUID]

}
