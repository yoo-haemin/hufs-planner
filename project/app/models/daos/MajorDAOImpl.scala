package models.daos

import java.time.Year
import java.util.UUID

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future

import models.Major
import models.MajorType

class MajorDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends MajorDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val majors = TableQuery[MajorsTable]

  def findById(id: UUID): Future[Seq[Major]] =
    db.run(majors.filter(_.id === id).result)

  def all(): Future[Seq[Major]] =
    db.run(majors.result)

  def insert(major: Major): Future[UUID] = {
    db.run(majors returning majors.map(_.id) += major)
  }

  class MajorsTable(tag: Tag) extends Table[Major](tag, "majors") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def majorType = column[MajorType]("major_type")
    def year = column[Year]("year")

    def * = (id, name, majorType, year) <> (Major.tupled, Major.unapply _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)

  implicit val majorTypeColumnType: BaseColumnType[MajorType] =
    MappedColumnType.base[MajorType, Int](MajorType.toInt _, MajorType.fromInt _)
}
