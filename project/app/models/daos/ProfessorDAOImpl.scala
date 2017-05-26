package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import java.util.UUID

import models.Professor

class ProfessorDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends ProfessorDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val professors = TableQuery[ProfessorsTable]

  def findByName(name: String): Future[Seq[Professor]] =
    db.run(professors.filter(p => p.name1 === name || p.name2 === name).result)

  def all(): Future[Seq[Professor]] =
    db.run(professors.result)

  def insert(Professor: Professor): Future[UUID] =
    db.run(professors returning professors.map(_.id) += Professor)

  class ProfessorsTable(tag: Tag) extends Table[Professor](tag, "professors") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name1 = column[String]("name1", O.SqlType("text"))
    def name2 = column[Option[String]]("name2", O.SqlType("text"))

    def * = (id, name1, name2) <> (Professor.tupled, Professor.unapply _)
  }
}
