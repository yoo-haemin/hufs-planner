package models.daos.hashmap

import javax.inject.Inject
import scala.concurrent.Future
import models.{ DepartmentTime }
import models.daos.DepartmentTimeDAO

class DepartmentTimeDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl) extends DepartmentTimeDAO {
  def findByDepartmentId(id: String): Future[Seq[DepartmentTime]] =
    Future.successful(DepartmentTimeDAOImpl.departmentTimes.filter(_.departmentId == id).toSeq)

  def all(): Future[Seq[DepartmentTime]] =
    Future.successful(DepartmentTimeDAOImpl.departmentTimes.toSeq)

  def insert(departmentTime: DepartmentTime): Future[String] =
    Future.successful {
      DepartmentTimeDAOImpl.departmentTimes = DepartmentTimeDAOImpl.departmentTimes + departmentTime
      departmentTime.departmentId
    }
}

object DepartmentTimeDAOImpl {
  var departmentTimes = Set[DepartmentTime]()
}
