package models.daos.hashmap

import java.util.UUID

import javax.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import models.daos.UserDAO
import scala.concurrent.Future

/**
 * Give access to the user object.
 */
class UserDAOImpl @Inject() extends UserDAO {
  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo): Future[Option[User]] =
    Future.successful(UserDAOImpl.users.filter(_.loginInfo == loginInfo).headOption)

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID): Future[Option[User]] =
    Future.successful(UserDAOImpl.users.filter(_.userID == userID).headOption)

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(user: User): Future[User] =
    Future.successful {
      UserDAOImpl.users = UserDAOImpl.users + user
      user
    }
}

object UserDAOImpl {
  var users = Set[User]()
}
