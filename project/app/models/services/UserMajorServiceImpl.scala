package models.services

import javax.inject.Inject
import java.util.UUID
import java.time.Year

import models.{ UserMajor, Major, Score }
import models.daos.UserMajorDAO
import scala.concurrent.Future

class UserMajorServiceImpl @Inject() (
  userMajorDAO: UserMajorDAO
) extends UserMajorService {

  /**
   * Retrieves majors of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @return The retrieved majors
   */
  def allUserMajor(userID: UUID): Future[Seq[UserMajor]] = userMajorDAO.allMajor(userID)

  /**
   *
   */
  def save(userMajor: UserMajor): Future[UserMajor] = userMajorDAO.save(userMajor)

  def save(userID: UUID, majorID: UUID): Future[UserMajor] = save(UserMajor(userID, majorID))
}
