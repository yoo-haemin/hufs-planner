package models.daos.hashmap

import javax.inject.Inject

import scala.concurrent.Future

import models.{ Department }

import models.daos.DepartmentDAO
/**
 * Give access to the [[Department]] object.
 */
class DepartmentDAOImpl @Inject() extends DepartmentDAO {
  def findById(id: String): Future[Department] =
    Future.successful(DepartmentDAOImpl.departments.filter(_.id == id).head)

  def all(): Future[Seq[Department]] =
    Future.successful(DepartmentDAOImpl.departments.toSeq)

  def insert(department: Department): Future[String] =
    Future.successful {
      DepartmentDAOImpl.departments = DepartmentDAOImpl.departments + department
      department.id
    }
}

object DepartmentDAOImpl {
  var departments = Set[Department]()
}
