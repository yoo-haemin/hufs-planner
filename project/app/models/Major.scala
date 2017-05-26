package models

import java.time.Year
import java.util.UUID

case class Major(id: UUID, name: String, majorType: MajorType, year: Year)

sealed abstract class MajorType(v: Int)

object MajorType {
  case object FirstMajor extends MajorType(1)
  case object SecondMajor extends MajorType(2)
  case object FirstMajorMinor extends MajorType(3)
  case object Minor extends MajorType(4)
  case object MultipleMajor extends MajorType(5)
  case object TeacherCourse extends MajorType(6)

  def fromInt(v: Int): MajorType = v match {
    case 1 => FirstMajor
    case 2 => SecondMajor
    case 3 => FirstMajorMinor
    case 4 => Minor
    case 5 => MultipleMajor
    case 6 => TeacherCourse
  }

  def toInt(mt: MajorType): Int = mt match {
    case FirstMajor => 1
    case SecondMajor => 2
    case FirstMajorMinor => 3
    case Minor => 4
    case MultipleMajor => 5
    case TeacherCourse => 6
  }
}
