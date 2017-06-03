package models.services

import java.util.UUID
import models.{ UserMajor }
import scala.concurrent.Future

trait UserMajorService {

  /**
   * Retrieves majors of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of major to return
   * @return The retrieved majors
   */
  def allUserMajor(userID: UUID): Future[Seq[UserMajor]]

  /**
   *
   */
  def save(userMajor: UserMajor): Future[UserMajor]

  def save(userID: UUID, majorID: UUID): Future[UserMajor]
}
