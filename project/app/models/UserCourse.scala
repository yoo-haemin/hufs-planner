package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }

/**
 * The user object.
 *
 * @param userID The unique ID of the user.
 * @param courseID The ID of the course.
 * @param future The type of this course selection.
 */
case class UserCourse(
  userID: UUID,
  courseID: UUID,
  future: Boolean
)
