package models

import java.util.UUID

/**
 * The user-has-course object.
 */
case class UserCourse(
  userID: UUID,
  courseID: UUID,
  score: Score,
  future: Boolean = false,
  retake: Boolean = false
)
