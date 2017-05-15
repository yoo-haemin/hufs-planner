package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import models.Subject

class SubjectDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends SubjectDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val subjects = TableQuery[SubjectsTable]

  def findByDepartmentId(id: String): Future[Subject] =
    db.run(subjects.filter(_.departmentId === id).result.head)

  def findByName(name: String): Future[Subject] =
    db.run(subjects.filter(_.name === name).result.head)

  def all(): Future[Seq[Subject]] =
    db.run(subjects.result)

  def insert(Subject: Subject): Future[String] =
    db.run(subjects returning subjects.map(_.departmentId) += Subject)

  class SubjectsTable(tag: Tag) extends Table[Subject](tag, "subjects") {
    def departmentId = column[String]("department_id")
    def department = foreignKey("department_id_fk", departmentId, departmentDao.departments)(_.id)
    def name = column[String]("year")
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def * = (id, name, departmentId) <> (Subject.tupled, Subject.unapply _)
  }
}
