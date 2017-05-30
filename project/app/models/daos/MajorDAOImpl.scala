package models.daos
/*
import java.time.Year

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

  def findById(id: String): Future[Seq[Major]] =
    db.run(majors.filter(_.id === id).result)

  def findByName(name: String): Future[Seq[Major]] =
    db.run(majors.filter(m => m.nameKo === name || m.nameEn === name).result)

  def all(): Future[Seq[Major]] =
    db.run(majors.result)

  def insert(major: Major): Future[String] = {
    db.run(majors returning majors.map(_.id) += major)
  }

  class MajorsTable(tag: Tag) extends Table[Major](tag, "majors") {
    def id = column[String]("id", O.PrimaryKey)
    def nameKo = column[String]("name_ko")
    def nameEn = column[Option[String]]("name_en")
    def majorType = column[MajorType]("major_type")
    def year = column[Year]("year")

    def * = (id, nameKo, nameEn, majorType, year) <> (Major.tupled, Major.unapply _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)

  implicit val majorTypeColumnType: BaseColumnType[MajorType] =
    MappedColumnType.base[MajorType, Int](MajorType.toInt _, MajorType.fromInt _)
}

 */
