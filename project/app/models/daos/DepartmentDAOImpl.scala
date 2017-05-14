package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import models.{ Department, Campus, Affiliation }

/**
 * Give access to the [[Department]] object.
 */
class DepartmentDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends DepartmentDAO {
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

  class DepartmentsTable(tag: Tag) extends Table[Department](tag, "DEPARTMENT") {
    def id = column[String]("ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def campus = column[Campus]("CAMPUS")
    def affiliation = column[Affiliation]("AFFILIATION")

    def * = (id, name, campus, affiliation) <> (Department.tupled, Department.unapply _)
  }

  implicit val campusColumnType: BaseColumnType[Campus] =
    MappedColumnType.base[Campus, Int](Campus.toInt _, Campus.fromInt _)

  implicit val afiliationColumnType: BaseColumnType[Affiliation] =
    MappedColumnType.base[Affiliation, Int](Affiliation.toInt _, Affiliation.fromInt _)
}
