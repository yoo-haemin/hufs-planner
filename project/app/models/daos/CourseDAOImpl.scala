package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.driver.JdbcProfile

import scala.concurrent.Future
import fun.lambda.coursecrawler._
import java.time.DayOfWeek

class CourseDAOImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  private val courses = TableQuery[CoursesTable]

  private class DBCourse(
    val courseType: String,
    val courseYear: Option[Int],
    val courseNo: String,
    val courseName1: String,
    val courseName2: Option[String],
    val required: Boolean,
    val online: Boolean,
    val foreignLanguage: Boolean,
    val teamTeaching: Boolean,
    val professorNameMain: String,
    val professorNameAdditional: Option[String],
    val creditHours: Int,
    val courseHours: Int,
    val courseTime: Map[DayOfWeek, CourseTime],
    val currentlyEnrolled: Int,
    val maximumEnrolled: Option[Int],
    val note: Option[String],

    val id: Int,
    val departmentID: Int,
    val courseTimeID: Int,
    val departmentTimeYear: Int,
    val departmentTimeSemester: Int,
    val subjectDepartmentID: Int,
    val subjectID: Int
  ) extends Course(
    courseType, courseYear, courseNo, courseName1, courseName2, required, online, foreignLanguage,
    teamTeaching, professorNameMain, professorNameAdditional, creditHours, courseHours, courseTime,
    currentlyEnrolled, maximumEnrolled, note) {

    def toCourse = Course(courseType, courseYear, courseNo, courseName1, courseName2, required, online, foreignLanguage, teamTeaching, professorNameMain, professorNameAdditional, creditHours, courseHours, courseTime, currentlyEnrolled, maximumEnrolled, note)
  }

  def findById(id: Long): Future[Course] =
    db.run(courses.filter(_.id === id).result.head)

  def findByColor(color: String): DBIO[Option[Course]] =
    courses.filter(_.color === color).result.headOption

  def findByProjectId(projectId: Long): Future[List[Course]] =
    db.run(courses.filter(_.project === projectId).to[List].result)

  def partialUpdate(id: Long, color: Option[String], status: Option[CourseStatus.Value], project: Option[Long]): Future[Int] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val query = courses.filter(_.id === id)

    val update = query.result.head.flatMap { course =>
      query.update(course.patch(color, status, project))
    }

    db.run(update)
  }

  def all(): DBIO[Seq[Course]] =
    courses.result

  def insert(Course: Course): DBIO[Long] =
    courses returning courses.map(_.id) += Course

  def _deleteAllInProject(projectId: Long): DBIO[Int] =
    courses.filter(_.project === projectId).delete

  private class CoursesTable(tag: Tag) extends Table[DBCourse](tag, "COURSE") {
    def courseType = column[String]("course_type")
    def courseYear = column[Option[Int]]("course_year")
    def courseNo = column[String]("course_no")
    def courseName1 = column[String]("course_name_1")
    def courseName2 = column[Option[String]]("course_name_2")
    def required = column[Boolean]("required")
    def online = column[Boolean]("online")
    def foreignLanguage = column[Boolean]("foreign_language")
    def teamTeaching = column[Boolean]("team_teaching")
    def professorNameMain = column[String]("professorNameMain ")
    def professorNameAdditional = column[Option[String]]("professorNameAdditional ")
    def creditHours = column[Int]("creditHours ")
    def courseHours = column[Int]("courseHours ")
    def courseTime = column[Map[DayOfWeek, CourseTime]]("courseTime ")
    def currentlyEnrolled = column[Int]("currentlyEnrolled ")
    def maximumEnrolled = column[Option[Int]]("maximumEnrolled ")
    def note = column[Option[String]]("note ")

    def id = column[Int]("id ")
    def departmentID = column[Int]("departmentID ")
    def courseTimeID = column[Int]("courseTimeID ")
    def departmentTimeYear = column[Int]("departmentTimeYear ")
    def departmentTimeSemester = column[Int]("departmentTimeSemester ")
    def subjectDepartmentID = column[Int]("subjectDepartmentID ")
    def subjectID = column[Int]("subjectID")

    def * = (id, color, status, project) <> (Course.tupled, Course.unapply)
  }

  implicit val courseStatusColumnType = MappedColumnType.base[CourseStatus.Value, String](
    _.toString, string => CourseStatus.withName(string))
}
