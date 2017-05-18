package models.daos

import javax.inject.Inject
import java.util.UUID
import play.api.db.slick.DatabaseConfigProvider
import slick.collection.heterogeneous.HNil
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import fun.lambda.coursecrawler.Course
import scala.concurrent.ExecutionContext.Implicits.global

class CourseDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider)(
  protected val courseTimeDao: CourseTimeDAO) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
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
    val currentlyEnrolled: Int,
    val maximumEnrolled: Option[Int],
    val note: Option[String],

    val id: Int,
    val departmentID: Int,
    val courseTimeID: Int,
    val departmentTimeYear: Int,
    val departmentTimeSemester: Int,
    val subjectDepartmentID: Int,
    val subjectID: Int) {

    def toCourse: Future[Course] = {
      for {
        courseTime <- courseTimeDao.findById(this.id)
        courseTimeMap = courseTime.getOrElse(
          new models.CourseTime(
            0, Seq[(java.time.DayOfWeek, fun.lambda.coursecrawler.CourseTime)]())).toMap
      } yield Course(
        courseType, courseYear, courseNo, courseName1, courseName2, required,
        online, foreignLanguage, teamTeaching, professorNameMain, professorNameAdditional,
        creditHours, courseHours, courseTimeMap, currentlyEnrolled, maximumEnrolled, note)
    }
  }

  def findById(id: Int): Future[Course] =
    db.run(courses.filter(_.id === id).result.head).flatMap(_.toCourse)

  private class CoursesTable(tag: Tag) extends Table[DBCourse](tag, "courses") {
    def courseType = column[String]("course_type")
    def courseYear = column[Option[Int]]("course_year")
    def courseNo = column[String]("course_no")
    def courseName1 = column[String]("course_name_1")
    def courseName2 = column[Option[String]]("course_name_2")
    def required = column[Boolean]("required")
    def online = column[Boolean]("online")
    def foreignLanguage = column[Boolean]("foreign_language")
    def teamTeaching = column[Boolean]("team_teaching")
    def professorNameMain = column[String]("professor_name_main")
    def professorNameAdditional = column[Option[String]]("professor_name_additional")
    def creditHours = column[Int]("credit_hours")
    def courseHours = column[Int]("course_hours")
    def currentlyEnrolled = column[Int]("currently_enrolled")
    def maximumEnrolled = column[Option[Int]]("maximum_enrolled")
    def note = column[Option[String]]("note")

    def id = column[Int]("id")
    def departmentID = column[Int]("department_id")
    def courseTimeID = column[Int]("course_time_id")
    def departmentTimeYear = column[Int]("department_time_year")
    def departmentTimeSemester = column[Int]("department_time_semester")
    def subjectDepartmentID = column[Int]("subject_department_id")
    def subjectID = column[Int]("subject_id")
    def courseTime = column[Int]("course_time_id")

    def * = courseType :: courseYear :: courseNo :: courseName1 :: courseName2 :: required ::
      online :: foreignLanguage :: teamTeaching :: professorNameMain :: professorNameAdditional ::
      creditHours :: courseHours :: currentlyEnrolled :: maximumEnrolled :: note :: id :: departmentID ::
      courseTimeID :: departmentTimeYear :: departmentTimeSemester :: subjectDepartmentID :: subjectID ::
      HNil
  }
}
