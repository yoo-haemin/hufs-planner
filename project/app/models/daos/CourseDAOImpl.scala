package models.daos

import javax.inject.Inject
import java.util.UUID
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import java.time._
import models._

//import scala.concurrent.ExecutionContext.Implicits.global

class CourseDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider)(
  protected val courseTimeDao: CourseTimeDAO) extends CourseDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  private val courses = TableQuery[CoursesTable]

  def findById(id: UUID): Future[Course] =
    db.run(courses.filter(_.id === id).result.head)

  private class CoursesTable(tag: Tag) extends Table[Course](tag, "courses") {
    def id = column[UUID]("id", O.SqlType("BINARY(16) NOT NULL"))
    def codePrefix = column[String]("code_prefix", O.SqlType("CHAR(7) NOT NULL"))
    def codeSuffix = column[String]("code_suffix", O.SqlType("TEXT NULL"))
    def departmentTimeYear = column[Year]("department_time_year", O.SqlType("YEAR NOT NULL"))
    def departmentTimeSemester = column[Semester]("department_time_semester", O.SqlType("TINYINT NOT NULL"))
    def departmentId = column[String]("department_id", O.SqlType("CHAR(10) NOT NULL"))
    def subjectsId = column[UUID]("subjects_id", O.SqlType("BINARY(16) NOT NULL"))
    def professorsId = column[UUID]("professors_id", O.SqlType("BINARY(16) NOT NULL"))
    def courseAreasId = column[UUID]("course_areas_id", O.SqlType("BINARY(16) NOT NULL"))
    def courseTimesId = column[UUID]("course_times_id", O.SqlType("BINARY(16) NULL"))
    def courseType = column[Option[String]]("type", O.SqlType("VARCHAR(20) NULL"))
    def name1 = column[String]("name_1", O.SqlType("VARCHAR(45) NULL"))
    def name2 = column[Option[String]]("name_2", O.SqlType("VARCHAR(45) NULL"))
    def creditHours = column[Byte]("credit_hours", O.SqlType("TINYINT NULL"))
    def courseHours = column[Byte]("course_hours", O.SqlType("TINYINT NULL"))
    def required = column[Boolean]("required", O.SqlType("BOOLEAN"))
    def online = column[Boolean]("online", O.SqlType("BOOLEAN"))
    def foreignLanguage = column[Boolean]("foreign_language", O.SqlType("BOOLEAN"))
    def teamTeaching = column[Boolean]("team_teaching", O.SqlType("BOOLEAN"))

    def * = (id, codePrefix, codeSuffix, departmentTimeYear, departmentTimeSemester, departmentId, subjectsId,
      professorsId, courseAreasId, courseTimesId, courseType, name1, name2, creditHours, courseHours,
      required, online, foreignLanguage, teamTeaching) <> (Course.tupled, Course.unapply _)

    implicit val yearColumnType: BaseColumnType[Year] = MappedColumnType.base[Year, Int](_.getValue, Year.of _)
    implicit val semesterColumnType: BaseColumnType[Semester] = MappedColumnType.base[Semester, Byte](Semester.toByte _, Semester.fromByte _)
  }
}
