package models

sealed abstract class Semester(val v: Int) {
  override def toString = this match {
    case Semester.FirstSemester => "first.semester"
    case Semester.SummerSemester => "summer.semester"
    case Semester.SecondSemester => "second.semester"
    case Semester.WinterSemester => "winter.semester"
  }
}

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
