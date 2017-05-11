package models.daos

import java.time.Year
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import java.time.Year
import models.{ DepartmentTime, Semester }

class DepartmentTimeDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends DepartmentTimeDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  val departmentTimes = TableQuery[DepartmentTimesTable]

  def findByDepartmentId(id: String): Future[DepartmentTime] =
    db.run(departmentTimes.filter(_.departmentId === id).result.head)

  def all(): Future[Seq[DepartmentTime]] =
    db.run(departmentTimes.result)

  def insert(DepartmentTime: DepartmentTime): Future[String] =
    db.run(departmentTimes returning departmentTimes.map(_.departmentId) += DepartmentTime)

  class DepartmentTimesTable(tag: Tag) extends Table[DepartmentTime](tag, "department_time") {
    def departmentId = column[String]("department_id")
    def departmentIdFk = foreignKey("department_id_fk", departmentId, departmentDao.departments)(_.id)
    def year = column[Year]("year", O.PrimaryKey)
    def semester = column[Semester]("semester", O.PrimaryKey)

    def * = (departmentId, year, semester) <> (DepartmentTime.tupled, DepartmentTime.unapply _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)

  implicit val semesterColumnType: BaseColumnType[Semester] =
    MappedColumnType.base[Semester, Byte](Semester.toByte _, Semester.fromByte _)
}
