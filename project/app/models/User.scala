package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

/**
 * The user object.
 *
 * @param userID The unique ID of the user.
 * @param loginInfo The linked login info.
 * @param firstName Maybe the first name of the authenticated user.
 * @param lastName Maybe the last name of the authenticated user.
 * @param fullName Maybe the full name of the authenticated user.
 * @param email Maybe the email of the authenticated provider.
 * @param avatarURL Maybe the avatar URL of the authenticated provider.
 * @param activated Indicates that the user has activated its registration.
 */
case class User(
  userID: UUID,
  loginInfo: LoginInfo,
  email: Option[String],
  classYear: Int,
  semester: Int,
  activated: Boolean
) extends Identity
