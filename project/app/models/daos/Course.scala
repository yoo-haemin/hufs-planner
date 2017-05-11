package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Course() {

}

class CourseRepo @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  private val Courses = TableQuery[CoursesTable]

  def findById(id: Long): Future[Course] =
    db.run(Courses.filter(_.id === id).result.head)

  def findByColor(color: String): DBIO[Option[Course]] =
    Courses.filter(_.color === color).result.headOption

  def findByProjectId(projectId: Long): Future[List[Course]] =
    db.run(Courses.filter(_.project === projectId).to[List].result)

  def findByReadyStatus: DBIO[List[Course]] =
    Courses.filter(_.status === CourseStatus.ready).to[List].result

  def partialUpdate(id: Long, color: Option[String], status: Option[CourseStatus.Value], project: Option[Long]): Future[Int] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val query = Courses.filter(_.id === id)

    val update = query.result.head.flatMap { course =>
      query.update(course.patch(color, status, project))
    }

    db.run(update)
  }

  def all(): DBIO[Seq[Course]] =
    Courses.result

  def insert(Course: Course): DBIO[Long] =
    Courses returning Courses.map(_.id) += Course

  def _deleteAllInProject(projectId: Long): DBIO[Int] =
    Courses.filter(_.project === projectId).delete

  private class CoursesTable(tag: Tag) extends Table[Course](tag, "COURSE") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def color = column[String]("COLOR")
    def status = column[CourseStatus.Value]("STATUS")
    def project = column[Long]("PROJECT")

    def * = (id, color, status, project) <> (Course.tupled, Course.unapply)
    def ? = (id.?, color.?, status.?, project.?).shaped.<>({ r => import r._; _1.map(_ => Course.tupled((_1.get, _2.get, _3.get, _4.get))) }, (_: Any) => throw new Exception("Inserting into ? Courseion not supported."))
  }

  implicit val courseStatusColumnType = MappedColumnType.base[CourseStatus.Value, String](
    _.toString, string => CourseStatus.withName(string))

}
