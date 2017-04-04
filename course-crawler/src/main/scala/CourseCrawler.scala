package kr.pe.zzz

package object course_crawler {
  import java.time.Year

  type CourseArea = Map[(Year, Semester), CourseDivision]
}
