package models.services

import javax.inject.Inject
import models.daos.DepartmentDAO
import models.{ Department }

import scala.concurrent.Future

class DepartmentServiceImpl @Inject() (departmentDAO: DepartmentDAO) extends DepartmentService {
  def findById(id: String): Future[Department] = departmentDAO.findById(id)

  def findByName(name: String): Future[Option[Department]] = departmentDAO.findByName(name)

  def all(): Future[Seq[Department]] = departmentDAO.all()

  def insert(department: Department): Future[String] = departmentDAO.insert(department)
}
