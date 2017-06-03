package models

import java.time.Year

case class DepartmentTime(
  departmentId: String,
  year: Year,
  semester: Semester
)

sealed abstract class Semester(val v: Int)

object Semester {
  import scala.language.implicitConversions
  final object FirstSemester extends Semester(1)
  final object SummerSemester extends Semester(2)
  final object SecondSemester extends Semester(3)
  final object WinterSemester extends Semester(4)

  implicit def fromInt(b: Int): Semester = b match {
    case 1 => FirstSemester
    case 2 => SummerSemester
    case 3 => SecondSemester
    case 4 => WinterSemester
  }

  implicit def toInt(s: Semester): Int = s match {
    case FirstSemester => 1
    case SummerSemester => 2
    case SecondSemester => 3
    case WinterSemester => 4
  }
}
