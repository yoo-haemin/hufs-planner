package models

import java.util.UUID
import java.time.Year

case class Course(
  id: UUID,
  codePrefix: String,
  codeSuffix: String,
  departmentTimeYear: Year,
  departmentTimeSemester: Semester,
  departmentId: String,
  subjectsId: UUID,
  professorsId: UUID,
  courseAreasId: UUID,
  courseTimesId: UUID,
  courseType: Option[String],
  name1: String,
  name2: Option[String],
  creditHours: Byte,
  courseHours: Byte,
  required: Boolean,
  online: Boolean,
  foreignLanguage: Boolean,
  teamTeaching: Boolean
)
