package kr.pe.zzz.course_crawler

sealed abstract class Semester(val value: Int)

case object Semester {
  final case object FirstSemester extends Semester(1)
  final case object SummerSemester extends Semester(2)
  final case object SecondSemester extends Semester(3)
  final case object WinterSemester extends Semester(4)
}

trait CourseDivision
case class Major(name: String, name2: String, code: String, courses: Seq[Course]) extends CourseDivision
case class LiberalArts(name1: String, code: String, courses: Seq[Course]) extends CourseDivision

object Major {
  def apply() = {

  }
}
