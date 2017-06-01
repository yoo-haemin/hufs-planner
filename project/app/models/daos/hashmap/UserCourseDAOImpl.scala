package models.daos.hashmap

import java.util.UUID

import javax.inject.Inject
import models.{ User, Course, UserCourse }
import models.daos.{ UserCourseDAO, CourseDAO, UserDAO }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Give access to the user object.
 */
class UserCourseDAOImpl @Inject() (courseDAO: CourseDAO) extends UserCourseDAO {
  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[Course]] = {
    val courseList = UserCourseDAOImpl.userCourses.filter(_.userID == userID).map(_.courseID).toSeq
    //Seq[Future[Course]] -> Future[Seq[Course]]
    courseList.foldLeft(Future.successful(Seq[Course]())) { (acc, id) =>
      (acc zip courseDAO.findById(id)).map(p => p._1 :+ p._2)
    }
  }

  def save(userCourse: UserCourse): Future[UserCourse] = {
    Future.successful {
      UserCourseDAOImpl.userCourses = UserCourseDAOImpl.userCourses + userCourse
      userCourse
    }
  }
}

object UserCourseDAOImpl {
  var userCourses = Set[UserCourse]()
}
