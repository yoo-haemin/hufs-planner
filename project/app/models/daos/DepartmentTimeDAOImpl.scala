package models.daos
/*
import java.time.Year
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import java.time.Year
import models.{ DepartmentTime, Semester }

class DepartmentTimeDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends DepartmentTimeDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val departmentTimes = TableQuery[DepartmentTimesTable]

  def findByDepartmentId(id: String): Future[Seq[DepartmentTime]] =
    db.run(departmentTimes.filter(_.departmentId === id).result)

  def all(): Future[Seq[DepartmentTime]] =
    db.run(departmentTimes.result)

  def insert(DepartmentTime: DepartmentTime): Future[String] =
    db.run(departmentTimes returning departmentTimes.map(_.departmentId) += DepartmentTime)

  class DepartmentTimesTable(tag: Tag) extends Table[DepartmentTime](tag, "departments_with_time") {
    def year = column[Year]("year", O.PrimaryKey, O.SqlType("year"))
    def semester = column[Semester]("semester", O.PrimaryKey, O.SqlType("tinyint"))
    def departmentId = column[String]("department_id", O.SqlType("char(10)"))

    def * = (departmentId, year, semester) <> (DepartmentTime.tupled, DepartmentTime.unapply _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)

  implicit val semesterColumnType: BaseColumnType[Semester] =
    MappedColumnType.base[Semester, Byte](Semester.toByte _, Semester.fromByte _)
}

 */
