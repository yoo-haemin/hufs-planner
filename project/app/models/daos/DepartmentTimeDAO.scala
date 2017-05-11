package models.daos

import scala.concurrent.Future
import models.{ DepartmentTime }

trait DepartmentTimeDAO {
  def findByDepartmentId(id: String): Future[DepartmentTime]

  def all(): Future[Seq[DepartmentTime]]

  def insert(DepartmentTime: DepartmentTime): Future[String]
}
