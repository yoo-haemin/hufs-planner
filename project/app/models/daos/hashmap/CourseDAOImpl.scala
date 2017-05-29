package models.daos.hashmap

import javax.inject.Inject
import java.util.UUID
import scala.concurrent.Future
import models._
import models.daos.CourseDAO

//import scala.concurrent.ExecutionContext.Implicits.global

class CourseDAOImpl @Inject() extends CourseDAO {
  def findById(id: UUID): Future[Course] =
    Future.successful(CourseDAOImpl.courses.filter(_.id == id).head)
}

object CourseDAOImpl {
  var courses = Set[Course]()
}
