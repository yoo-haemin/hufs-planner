package models

import java.util.UUID
import java.time.Year

case class Course(
  id: UUID,
  codePrefix: String,
  codeSuffix: String,
  departmentId: String,
  year: Year,
  semester: Semester,
  professorsId: UUID,
  courseAreasId: UUID,
  courseTimesId: UUID,
  //courseType: Option[String],
  name1: String,
  name2: Option[String],
  creditHours: Byte,
  courseHours: Byte,
  //recommendedYear: Int,
  online: Boolean = false,
  foreignLanguage: Boolean = false,
  teamTeaching: Boolean = false
)
