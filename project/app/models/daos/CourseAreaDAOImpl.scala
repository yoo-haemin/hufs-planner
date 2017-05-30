package models.daos
/*
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import java.util.UUID

import models.CourseArea

class CourseAreaDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl)(
  protected val dbConfigProvider: DatabaseConfigProvider) extends CourseAreaDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val courseAreas = TableQuery[CourseAreasTable]

  def findByValue(value: String): Future[CourseArea] =
    db.run(courseAreas.filter(_.value === value).result.head)

  def all(): Future[Seq[CourseArea]] =
    db.run(courseAreas.result)

  def insert(CourseArea: CourseArea): Future[UUID] =
    db.run(courseAreas returning courseAreas.map(_.id) += CourseArea)

  class CourseAreasTable(tag: Tag) extends Table[CourseArea](tag, "courseAreas") {
    def id = column[UUID]("id", O.PrimaryKey)
    def value = column[String]("value", O.SqlType("text"))

    def * = (id, value) <> (CourseArea.tupled, CourseArea.unapply _)
  }
}

 */
