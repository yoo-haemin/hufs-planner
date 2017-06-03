package models

import java.util.UUID
import java.time.Year

import MajorType._

case class Major(id: UUID, nameKo: String, nameEn: Option[String], majorType: MajorType, year: Year) {
  //TODO move this to other appropriate place
  def required: Int = year match {
    case i if i.getValue <= 2014 => majorType match {
      case FirstMajor => 54
      case SecondMajor => 54
      case FirstMajorMinor => 75
      case Minor => 21
      case MultipleMajor => 0
      case TeacherCourse => 0
      case LiberalArts => 26
      case FreeCourse => 0
    }
    case _ => 0
  }
}
