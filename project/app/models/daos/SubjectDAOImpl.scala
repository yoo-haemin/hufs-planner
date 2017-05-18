package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import java.util.UUID

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
    def id = column[UUID]("id", O.PrimaryKey)
    def code = column[String]("code", O.SqlType("char(7)"))
    def departmentId = column[String]("department_id", O.SqlType("char(10)"))
    def name = column[String]("year", O.SqlType("text"))

    def * = (id, code, departmentId, name) <> (Subject.tupled, Subject.unapply _)
  }
}
