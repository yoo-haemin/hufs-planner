package models

import java.util.UUID

case class Professor(
  id: UUID,
  name1: String,
  name2: Option[String]
)
