package kr.pe.zzz


package object course_crawler {
  import java.time.{ DayOfWeek, Year }
  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._

  type CourseArea = Map[(Year, Semester), CourseDivision]

  // implicit val courseTimeReads: Reads[Map[DayOfWeek,CourseTime]] = {

  // }


  implicit val courseReads: Reads[Course] = (
    (__ \ "courseType")               .read[String] and
      (__ \ "courseYear")             .read[Int] and
      (__ \ "courseNo")               .read[String] and
      (__ \ "courseName1")            .read[String] and
      (__ \ "courseName2")            .read[String] and
      (__ \ "required")               .read[Boolean] and
      (__ \ "online")                 .read[Boolean] and
      (__ \ "foreignLanguage")        .read[Boolean] and
      (__ \ "teamTeaching")           .read[Boolean] and
      (__ \ "professorNameMain")      .read[String] and
      (__ \ "professorNameAdditional").read[String] and
      (__ \ "creditHours")            .read[Int] and
      (__ \ "courseHours")            .read[Int] and
      (__ \ "courseTime")             .read[Map[DayOfWeek,CourseTime]] and
      (__ \ "currentlyEnrolled")      .read[Int] and
      (__ \ "maximumEnrolled")        .read[Int] and
      (__ \ "note")                   .read[String]
  )



//   implicit val courseTimeReads = Reads[CourseTime] = (

//   )


//  implicit val courseWrites = new Writes[]


  implicit val courseTimeWrites: Writes[CourseTime] = (
    (JsPath \ "time").write[Set[Int]] and
      (JsPath \ "classroom").write[String]
  )(unlift(CourseTime.unapply))

  implicit val dayOfWeekWrites = new Writes[DayOfWeek] {
    def writes(dayOfWeek: DayOfWeek) = dayOfWeek match {
      case DayOfWeek.MONDAY => Json.parse("Mon")
      case DayOfWeek.TUESDAY => Json.parse("Tue")
      case DayOfWeek.WEDNESDAY => Json.parse("Wed")
      case DayOfWeek.THURSDAY => Json.parse("Thu")
      case DayOfWeek.FRIDAY => Json.parse("Fri")
      case DayOfWeek.SATURDAY => Json.parse("Sat")
      case DayOfWeek.SUNDAY => Json.parse("Sun")
      case _ => JsNull
    }
  }



//  implicit val courseWrites = Json.writes[Course]


}
