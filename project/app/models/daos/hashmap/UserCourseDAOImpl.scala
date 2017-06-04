package models.daos.hashmap

import java.util.UUID

import javax.inject.Inject
import models.{ User, Course, UserCourse, Score }
import models.daos.{ UserCourseDAO, CourseDAO, UserDAO }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Give access to the user object.
 */
class UserCourseDAOImpl @Inject() (courseDAO: CourseDAO) extends UserCourseDAO {
  import UserCourseDAOImpl._

  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[UserCourse]] = {
    Future.successful(UserCourseDAOImpl.userCourses.filter(_.userID == userID).toSeq)
  }

  def save(userCourse: UserCourse): Future[UserCourse] = {
    Future.successful {
      UserCourseDAOImpl.userCourses = UserCourseDAOImpl.userCourses + userCourse
      userCourse
    }
  }

  def remove(userCourse: UserCourse): Future[Unit] = {
    Future.successful(userCourses = userCourses - userCourse)
  }
}

object UserCourseDAOImpl {
  val sampleUser = UUID.fromString("42944783-ee3f-4a92-85b9-777c94d9dc5e")

  var userCourses = Set[UserCourse](
    UserCourse(sampleUser, UUID.fromString("16df05fa-46e2-11e7-8db2-d413de251a16"), 9),
    UserCourse(sampleUser, UUID.fromString("172f36c4-46e2-11e7-8db2-d413de251a16"), 8, retake = true),
    UserCourse(sampleUser, UUID.fromString("1872168c-46e2-11e7-8db2-d413de251a16"), 7),
    UserCourse(sampleUser, UUID.fromString("18c51b45-46e2-11e7-8db2-d413de251a16"), 0),
    UserCourse(sampleUser, UUID.fromString("aa35becc-7a81-45f6-9d31-3268115f2aac"), 2)
  )
}
