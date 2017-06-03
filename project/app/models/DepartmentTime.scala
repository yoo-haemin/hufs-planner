package models

import java.time.Year

case class DepartmentTime(
  departmentId: String,
  year: Year,
  semester: Semester
)
