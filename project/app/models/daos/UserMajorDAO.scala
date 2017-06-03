package models.daos

import java.util.UUID

import models.{ Major, UserMajor }

import scala.concurrent.Future

/**
 * Give access to the user object.
 */
trait UserMajorDAO {
  def allMajor(userID: UUID, future: Boolean = false): Future[Seq[UserMajor]]

  def save(userMajor: UserMajor): Future[UserMajor]
}
