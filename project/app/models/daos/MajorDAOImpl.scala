package models.daos

import java.time.Year
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future
import models.Major

class MajorDAOImpl @Inject() (
  protected val majorTypeDao: MajorTypeDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends MajorDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  val majors = TableQuery[MajorsTable]

  def findById(id: String): Future[Seq[Major]] =
    db.run(majors.filter(_.id === id).result)

  def all(): Future[Seq[Major]] =
    db.run(majors.result)

  def insert(major: Major): Future[String] = {
    db.run(majors returning majors.map(_.id) += major)
  }

  class MajorsTable(tag: Tag) extends Table[Major](tag, "department_time") {
    def id = column[String]("id")
    def majorType = column[Int]("major_type_id")
    def majorTypeId = foreignKey("major_type_id_fk", majorType, majorTypeDao.majorTypes)(_.id)
    def year = column[Year]("year", O.PrimaryKey)

    def * = (id, majorType, year) <> (Major.tupled, Major.unapply _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)
}
