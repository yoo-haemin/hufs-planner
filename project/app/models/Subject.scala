package models

case class Subject(
  id: java.util.UUID,
  code: String,
  departmentId: String,
  name: String
)
