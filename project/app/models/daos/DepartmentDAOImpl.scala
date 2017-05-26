package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import models.{ Department, Campus, Affiliation }

/**
 * Give access to the [[Department]] object.
 */
class DepartmentDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends DepartmentDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val departments = TableQuery[DepartmentsTable]

  def findById(id: String): Future[Department] =
    db.run(departments.filter(_.id === id).result.head)

  def all(): Future[Seq[Department]] =
    db.run(departments.result)

  def insert(Department: Department): Future[String] =
    db.run(departments returning departments.map(_.id) += Department)

  class DepartmentsTable(tag: Tag) extends Table[Department](tag, "departments") {
    def id = column[String]("id", O.PrimaryKey, O.SqlType("char(10)"))
    def name = column[String]("name", O.SqlType("varchar(45)"))
    def campus = column[Campus]("campus", O.SqlType("tinyint"))
    def affiliation = column[Affiliation]("affiliation", O.SqlType("tinyint"))

    def * = (id, name, campus, affiliation) <> (Department.tupled, Department.unapply _)
  }

  implicit val campusColumnType: BaseColumnType[Campus] =
    MappedColumnType.base[Campus, Byte](Campus.toByte _, Campus.fromByte _)

  implicit val afiliationColumnType: BaseColumnType[Affiliation] =
    MappedColumnType.base[Affiliation, Byte](Affiliation.toByte _, Affiliation.fromByte _)
}
