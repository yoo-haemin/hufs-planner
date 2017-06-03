package models

import java.util.UUID

/**
 * The user-has-course object.
 */
case class UserMajor(
  userID: UUID,
  majorID: UUID
)
