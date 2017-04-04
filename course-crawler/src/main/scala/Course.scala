package kr.pe.zzz.course_crawler


import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


sealed abstract class DayOfWeek(val value: Int) {
  def unapply:
}

case object DayOfWeek {
  final case object Monday extends DayOfWeek(1)
  final case object Tuesday extends DayOfWeek(2)
  final case object Wednesday extends DayOfWeek(3)
  final case object Thursday extends DayOfWeek(4)
  final case object Friday extends DayOfWeek(5)
  final case object Saturday extends DayOfWeek(6)
  final case object Sunday extends DayOfWeek(7)
}


case class CourseTime(
  time: Set[Int], classroom: String) {
  override def toString = {
    s"""${time.toString}, "$classroom")"""
  }
}

case class Course(
  courseType             : String,
  courseYear             : Option[Int],
  courseNo               : String,
  courseNameMain         : String,
  courseNameAdditional   : String,
  professorNameMain      : String,
  professorNameAdditional: String,
  creditHours            : Int,
  courseHours            : Int,
  courseTime             : Map[DayOfWeek, CourseTime],
  currentlyEnrolled      : Int,
  maximumEnrolled        : Int,
  note                   : Option[String]) {
  override def toString = {
    val noteString = note match {
      case Some(s) => s""""$s""""
      case None => "null"
    }

    val courseTimeString = s"""{  }"""

    s"""{"courseType" : "$courseType", courseYear : "${courseYear.getOrElse("null").toString()}", ""courseNo" : "$courseNo", "courseNameMain" : "$courseNameMain", "courseNameAdditional" : "$courseNameAdditional", "professorNameMain" : "$professorNameMain", "professorNameAdditional" : "$professorNameAdditional", "creditHours" : $creditHours, "courseHours" : $courseHours, "courseTime" : "$courseTime", "currentlyEnrolled" : $currentlyEnrolled, "maximumEnrolled" : $maximumEnrolled, "noteString" : $noteString }"""
  }
}


object Course {
//   implicit val locationReads: Reads[Location] = (
//   (JsPath \ "lat").read[Double](min(-90.0) keepAnd max(90.0)) and
//   (JsPath \ "long").read[Double](min(-180.0) keepAnd max(180.0))
// )(Location.apply _)

// implicit val residentReads: Reads[Resident] = (
//   (JsPath \ "name").read[String](minLength[String](2)) and
//   (JsPath \ "age").read[Int](min(0) keepAnd max(150)) and
//   (JsPath \ "role").readNullable[String]
// )(Resident.apply _)

//   implicit val placeReads: Reads[Place] = (
//     (JsPath \ "name").read[String](minLength[String](2)) and
//       (JsPath \ "location").read[Location] and
//       (JsPath \ "residents").read[Seq[Resident]]
//   )(Place.apply _)

//   implicit val courseTimeReads = Reads[CourseTime] = (

//   )

  implicit val courseTimeWrites: Writes[CourseTime] = (
    (JsPath \ "time").write[Set[Int]] and
      (JsPath \ "classroom").write[String]
  )(unlift(CourseTime.unapply))

  implicit val dayOfWeekWrites: Writes[DayOfWeek] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "location").write[Location] and
      (JsPath \ "residents").write[Seq[Resident]]
  )(unlift(DayOfWeek.unapply))


  implicit val courseWrites = Json.writes[Course]

  def newCourse(courseDetails: List[Option[String]]): Course = {
    val courseType: String = courseDetails(0).getOrElse("-")
    val courseYear: Option[Int] = courseDetails(1).map(_.toInt)
    val courseNo: String = courseDetails(2).getOrElse("-")
    val courseNameMain: String = courseDetails(3).getOrElse("-")
    val courseNameAdditional: String = courseDetails(4).getOrElse("-")
    val professorNameMain: String = courseDetails(5).getOrElse("-")
    val professorNameAdditional: String = courseDetails(6).getOrElse("-")
    val creditHours: Int = courseDetails(7).get.toInt
    val courseHours: Int = courseDetails(8).get.toInt
    val currentlyEnrolled: Int = courseDetails(24).get.toInt
    val maximumEnrolled: Int = courseDetails(25).get.toInt
    val note: Option[String] = courseDetails(26)

    //getDateOfWeek(courseDetails(9)).get
    val courseTimeOriginal = //: Map[DayOfWeek, CourseTime] =
      Vector(
        (getDateOfWeek(courseDetails(9)),
          courseDetails(10), courseDetails(11), courseDetails(12),
          courseDetails(13)),
        (getDateOfWeek(courseDetails(14)),
          courseDetails(15), courseDetails(16), courseDetails(17),
          courseDetails(18)),
        (getDateOfWeek(courseDetails(19)),
          courseDetails(20), courseDetails(21), courseDetails(22),
          courseDetails(23))
      )

    val courseTime: Map[DayOfWeek, CourseTime] = (for {
      c <- courseTimeOriginal
      dateOfWeek <- c._1
      time1 <- c._2
      time2 <- c._3
      time3 <- c._4
      classroom <- c._5
    } yield {
      (dateOfWeek ->
        CourseTime(Set(time1.toInt, time2.toInt, time3.toInt), classroom))
    }).toMap

    Course(
      courseType,
      courseYear,
      courseNo,
      courseNameMain,
      courseNameAdditional,
      professorNameMain,
      professorNameAdditional,
      creditHours,
      courseHours,
      courseTime,
      currentlyEnrolled,
      maximumEnrolled,
      note
    )
  }

  def getDateOfWeek(d: Option[String]): Option[DayOfWeek] = d flatMap { s =>
    s match {
      case "Mon" => Some(java.time.DayOfWeek.MONDAY)
      case "Tue" => Some(java.time.DayOfWeek.TUESDAY)
      case "Wed" => Some(java.time.DayOfWeek.WEDNESDAY)
      case "Thu" => Some(java.time.DayOfWeek.THURSDAY)
      case "Fri" => Some(java.time.DayOfWeek.FRIDAY)
      case "Sat" => Some(java.time.DayOfWeek.SATURDAY)
      case "Sun" => Some(java.time.DayOfWeek.SUNDAY)
      case _ => None
    }
  }
}
