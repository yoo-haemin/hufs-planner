package models.daos.hashmap

import java.util.UUID

import javax.inject.Inject
import models.{ User, Major, UserMajor, Score }
import models.daos.{ UserMajorDAO, MajorDAO, UserDAO }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Give access to the user object.
 */
class UserMajorDAOImpl @Inject() (courseDAO: MajorDAO) extends UserMajorDAO {
  def allMajor(userID: UUID, future: Boolean = false): Future[Seq[UserMajor]] = {
    Future.successful(UserMajorDAOImpl.userMajors.filter(_.userID == userID).toSeq)
  }

  def save(userMajor: UserMajor): Future[UserMajor] = {
    Future.successful {
      UserMajorDAOImpl.userMajors = UserMajorDAOImpl.userMajors + userMajor
      userMajor
    }
  }
}

object UserMajorDAOImpl {
  var userMajors = Set[UserMajor](
    UserMajor(UUID.fromString("42944783-ee3f-4a92-85b9-777c94d9dc5e"), UUID.fromString("5756959c-46c4-11e7-8db2-d413de251a16")),
    UserMajor(UUID.fromString("42944783-ee3f-4a92-85b9-777c94d9dc5e"), UUID.fromString("58cbd8ec-46c4-11e7-8db2-d413de251a16")),
    UserMajor(UUID.fromString("42944783-ee3f-4a92-85b9-777c94d9dc5e"), UUID.fromString("130aff94-46e2-11e7-8db2-d413de251a16"))
  )
}
