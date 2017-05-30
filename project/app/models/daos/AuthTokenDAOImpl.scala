package models.daos
/*
import java.util.UUID

import javax.inject.Inject
import models.AuthToken
import org.joda.time.DateTime

import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Give access to the authToken object.
 */
class AuthTokenDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends AuthTokenDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val authTokens = TableQuery[AuthTokensTable]

  /**
   * Finds a authToken by its authToken ID.
   *
   * @param authTokenID The ID of the authToken to find.
   * @return The found authToken or None if no authToken for the given ID could be found.
   */
  def find(authTokenID: UUID) =
    db.run(authTokens.filter(_.authTokenID === authTokenID).result.headOption)

  /**
   * Finds expired tokens.
   *
   * @param dateTime The current date time.
   */
  def findExpired(dateTime: DateTime): Future[Seq[AuthToken]] =
    db.run(authTokens.filter(_.expiry < dateTime).result)

  /**
   * Saves a authToken.
   *
   * @param authToken The authToken to save.
   * @return The saved authToken.
   */
  def save(authToken: AuthToken) =
    db.run(authTokens returning authTokens += authToken)

  /**
   * Removes the token for the given ID.
   *
   * @param id The ID for which the token should be removed.
   * @return A future to wait for the process to be completed.
   */
  def remove(id: UUID): Future[Unit] = {
    db.run(authTokens.filter { _.authTokenID === id }.delete).flatMap(_ => Future.successful(()))
  }

  class AuthTokensTable(tag: Tag) extends Table[AuthToken](tag, "auth_tokens") {
    def authTokenID = column[UUID]("authtoken_id", O.PrimaryKey)
    def userId = column[UUID]("user_id")
    def expiry = column[DateTime]("expiry")
    def * = (authTokenID, userId, expiry) <> (AuthToken.tupled, AuthToken.unapply _)
  }

  implicit val dateTimeColumnType: BaseColumnType[DateTime] =
    MappedColumnType.base[DateTime, Long](_.getMillis(), new DateTime(_))
}

 */
