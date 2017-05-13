package models.daos

import java.util.UUID

import javax.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import models.User

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

/**
 * Give access to the user object.
 */
class UserDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends UserDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  val users = TableQuery[UsersTable]

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo) =
    db.run(users.filter(_.loginInfo === loginInfo).result.headOption)

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID) =
    db.run(users.filter(_.userID === userID).result.headOption)

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(user: User) =
    db.run(users returning users += user)

  class UsersTable(tag: Tag) extends Table[User](tag, "department_time") {
    def userID = column[UUID]("user_id", O.PrimaryKey)
    def loginInfo = column[LoginInfo]("login_info")
    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def fullName = column[Option[String]]("full_name")
    def email = column[Option[String]]("email")
    def avatarURL = column[Option[String]]("avatar_url")
    def activated = column[Boolean]("activated")
    def * = (userID, loginInfo, firstName, lastName, fullName, email, avatarURL, activated) <> (User.tupled, User.unapply _)
  }

  val loginInfoSeparator = "`>|"

  implicit val loginInfoColumnType: BaseColumnType[LoginInfo] =
    MappedColumnType.base[LoginInfo, String](
      { case LoginInfo(providerID, providerKey) => providerID + loginInfoSeparator + providerKey },
      { s =>
        val arr = s split loginInfoSeparator
        LoginInfo(arr(0), arr(1))
      })
}
