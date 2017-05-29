package models.daos.hashmap

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future
import models.CourseTime
import models.daos.CourseTimeDAO

class CourseTimeDAOImpl @Inject() extends CourseTimeDAO {
  def findById(id: UUID): Future[Option[CourseTime]] =
    Future.successful(CourseTimeDAOImpl.courseTimes.filter(_.id == id).headOption)

  def all(): Future[Seq[CourseTime]] =
    Future.successful(CourseTimeDAOImpl.courseTimes.toSeq)

  def insert(courseTime: CourseTime): Future[UUID] =
    Future.successful {
      CourseTimeDAOImpl.courseTimes = CourseTimeDAOImpl.courseTimes + courseTime
      courseTime.id
    }
}

object CourseTimeDAOImpl {
  var courseTimes = Set[CourseTime]()
}
