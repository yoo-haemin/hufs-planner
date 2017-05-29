package models.daos.hashmap

import models.daos.AuthTokenDAO

import java.util.UUID

import javax.inject.Inject
import models.AuthToken
import org.joda.time.DateTime

import scala.concurrent.Future

//import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Give access to the authToken object.
 */
class AuthTokenDAOImpl @Inject() extends AuthTokenDAO {

  /**
   * Finds a authToken by its authToken ID.
   *
   * @param authTokenID The ID of the authToken to find.
   * @return The found authToken or None if no authToken for the given ID could be found.
   */
  def find(authTokenID: UUID) =
    Future.successful(AuthTokenDAOImpl.authTokens.find(_.id == authTokenID))

  /**
   * Finds expired tokens.
   *
   * @param dateTime The current date time.
   */
  def findExpired(dateTime: DateTime): Future[Seq[AuthToken]] =
    Future.successful(AuthTokenDAOImpl.authTokens.filter(_.expiry isBefore dateTime).toSeq)

  /**
   * Saves a authToken.
   *
   * @param authToken The authToken to save.
   * @return The saved authToken.
   */
  def save(authToken: AuthToken) =
    Future.successful(
      {
        AuthTokenDAOImpl.authTokens = AuthTokenDAOImpl.authTokens + authToken
        authToken
      }
    )

  /**
   * Removes the token for the given ID.
   *
   * @param id The ID for which the token should be removed.
   * @return A future to wait for the process to be completed.
   */
  def remove(id: UUID): Future[Unit] =
    Future.successful(AuthTokenDAOImpl.authTokens = AuthTokenDAOImpl.authTokens.filterNot(_.id == id))
}

object AuthTokenDAOImpl {
  var authTokens = Set[AuthToken]()
}
