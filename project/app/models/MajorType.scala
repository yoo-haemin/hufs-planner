package models

sealed abstract class MajorType(v: Int) {
  override def toString: String = this match {
    case MajorType.FirstMajor => "first.major"
    case MajorType.SecondMajor => "second.major"
    case MajorType.FirstMajorMinor => "first.major.with.minor"
    case MajorType.Minor => "minor"
    case MajorType.MultipleMajor => "multiple.major"
    case MajorType.TeacherCourse => "teacher.course"
    case MajorType.LiberalArts => "liberal.arts"
    case MajorType.FreeCourse => "free.course"
  }
}

object MajorType {
  import scala.language.implicitConversions

  case object FirstMajor extends MajorType(1)
  case object SecondMajor extends MajorType(2)
  case object FirstMajorMinor extends MajorType(3)
  case object Minor extends MajorType(4)
  case object MultipleMajor extends MajorType(5)
  case object TeacherCourse extends MajorType(6)
  case object LiberalArts extends MajorType(7)
  case object FreeCourse extends MajorType(8)

  implicit def fromInt(v: Int): MajorType = v match {
    case 1 => FirstMajor
    case 2 => SecondMajor
    case 3 => FirstMajorMinor
    case 4 => Minor
    case 5 => MultipleMajor
    case 6 => TeacherCourse
    case 7 => LiberalArts
    case 8 => FreeCourse
  }

  implicit def toInt(mt: MajorType): Int = mt match {
    case FirstMajor => 1
    case SecondMajor => 2
    case FirstMajorMinor => 3
    case Minor => 4
    case MultipleMajor => 5
    case TeacherCourse => 6
    case LiberalArts => 7
    case FreeCourse => 8
  }
}
