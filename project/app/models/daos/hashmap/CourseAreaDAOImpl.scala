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
  var courseAreas = Set[CourseArea]()
}
