package models.daos.hashmap

import models.daos.CourseAreaDAO
import javax.inject.Inject
import scala.concurrent.Future
import java.util.UUID
import models.CourseArea

class CourseAreaDAOImpl @Inject() (
  protected val departmentDao: DepartmentDAOImpl) extends CourseAreaDAO {

  def findByValue(value: String): Future[CourseArea] =
    Future.successful(CourseAreaDAOImpl.courseAreas.filter(_.value == value).head)

  def all(): Future[Seq[CourseArea]] =
    Future.successful(CourseAreaDAOImpl.courseAreas.toSeq)

  def insert(courseArea: CourseArea): Future[UUID] =
    Future.successful {
      CourseAreaDAOImpl.courseAreas = CourseAreaDAOImpl.courseAreas + courseArea
      courseArea.id
    }
}

object CourseAreaDAOImpl {
  var courseAreas = Set[CourseArea](
    CourseArea(UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), "전공"),
    CourseArea(UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), "이중전공"),
    CourseArea(UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), "교양")
  )
}
