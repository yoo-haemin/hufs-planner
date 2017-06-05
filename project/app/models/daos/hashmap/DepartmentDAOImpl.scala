package models.daos.hashmap

import javax.inject.Inject

import scala.concurrent.Future

import models.{ Department, Campus, Affiliation }

import models.daos.DepartmentDAO
/**
 * Give access to the [[Department]] object.
 */
class DepartmentDAOImpl @Inject() extends DepartmentDAO {
  def findById(id: String): Future[Department] =
    Future.successful(DepartmentDAOImpl.departments.filter(_.id == id).head)

  def findByName(name: String): Future[Option[Department]] =
    Future.successful(DepartmentDAOImpl.departments.filter(_.name == name).headOption)

  def all(): Future[Seq[Department]] =
    Future.successful(DepartmentDAOImpl.departments.toSeq)

  def insert(department: Department): Future[String] =
    Future.successful {
      DepartmentDAOImpl.departments = DepartmentDAOImpl.departments + department
      department.id
    }
}

object DepartmentDAOImpl {
  var departments = Set[Department](
    Department("ATMB2_H1", "융복합소프트웨어전공", Campus.Seoul, Affiliation.Undergraduate),
    Department("ACDA1_H1", "언론정보전공", Campus.Seoul, Affiliation.Undergraduate),
    Department("AEAA1_H1", "국제통상학과", Campus.Seoul, Affiliation.Undergraduate),
    Department("GYOYANG", "미네르바교양대학", Campus.Seoul, Affiliation.Undergraduate)
  )
}
