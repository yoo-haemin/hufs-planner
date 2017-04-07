package kr.pe.zzz.course_crawler


import java.time.DayOfWeek
import scala.collection.immutable.SortedSet
import scala.util.Try
import scala.util.matching.Regex

case class CourseTime(time: SortedSet[Int], classroom: String) {
  def this(time: List[String], classroom: String) =
    this((SortedSet[Int]() /: time)((s, t) => Try(t.toInt).toOption match {
        case Some(i) => s + i
        case None => s
      }), classroom)
}

case class Course(
  courseType             : String,
  courseYear             : Int,
  courseNo               : String,
  courseName1            : String,
  courseName2            : Option[String],
  required               : Boolean,
  online                 : Boolean,
  foreignLanguage        : Boolean,
  teamTeaching           : Boolean,
  professorNameMain      : String,
  professorNameAdditional: Option[String],
  creditHours            : Int,
  courseHours            : Int,
  courseTime             : Map[DayOfWeek, CourseTime],
  currentlyEnrolled      : Int,
  maximumEnrolled        : Int,
  note                   : Option[String])

object Course {
  def fromSchoolCourseBody(body: String): Seq[Course] = {
    def getDayOfWeek(str: String): DayOfWeek = str match {
      case "Mon" => DayOfWeek.MONDAY
      case "Tue" => DayOfWeek.TUESDAY
      case "Wed" => DayOfWeek.WEDNESDAY
      case "Thu" => DayOfWeek.THURSDAY
      case "Fri" => DayOfWeek.FRIDAY
      case "Sat" => DayOfWeek.SATURDAY
      case "Sun" => DayOfWeek.SUNDAY
    }

    val courseRegex: Regex = """<td>(.*)<\/td>\s*<td>(\d)<\/td>\s*<td>([A-Z]\d{5}[A-Z0-9]\d{2})<\/td>\s*<td align="left">\s*<!--.*-->\s*<div\s*.*\s*.\s*<font class="txt_navy">(.*)<\/font><br>\s*(?:<font class=['"]txt_gray8['"]>\((.*)\)<\/font>\s*)?<\/div>\s*<\/td>\s*<td>\s*.*\s*.*\s*<\/td>\s*(?:<td>(.*)<\/td>\s*)(?:<td>(.*)<\/td>\s*)(?:<td>(.*)<\/td>\s*)(?:<td>(.*)<\/td>\s*)<td align="left">(.*)\s*(?:<br>\s*<font class="txt_gray8">\((.*)\)<\/font>)?\s*<\/td>\s*<td>(\d)<\/td>\s*<td>(\d)<\/td>\s*<td align="left">.*<br><font class="txt_gray8">\((?:([A-Z][a-z]{2}) (\d{1,2}) (\d{1,2})? ?(\d{1,2})? ?(?:\(((?:[C0-9]\d{3,4}(?:-1)?)|(?:B[12]-\d{2})|(?:사이버관 대강당))\)? ?)?)(?:([A-Z][a-z]{2}) (\d{1,2}) (\d{1,2})? ?(\d{1,2})? ?(?:\(((?:[C0-9]\d{3,4}(?:-1)?)|(?:B[12]-\d{2})|(?:사이버관 대강당))\)? ?)?)?(?:([A-Z][a-z]{2}) (\d{1,2}) (\d{1,2})? ?(\d{1,2})? ?(?:\(((?:[C0-9]\d{3,4}(?:-1)?)|(?:B[12]-\d{2})|(?:사이버관 대강당))\)? ?)?)?\)<\/font><\/td>\s*<td>(\d{1,})&nbsp;\/&nbsp;(\d{1,})<\/td>\s*<td align="left">(.+)?<br>""".r

    (for {
      c <- courseRegex findAllMatchIn body
    } yield c.subgroups).map { courseMatch =>
      Course(
        courseMatch(0),        //courseType
        courseMatch(1).toInt,  //courseYear
        courseMatch(2),        //courseNo
        courseMatch(3),        //courseName1
        if (courseMatch(4) == null) Some(courseMatch(4)) else None, //courseName2
        if (courseMatch(5).length > 0) true else false, //required
        if (courseMatch(6).length > 0) true else false, //online
        if (courseMatch(7).length > 0) true else false, //foreign language
        if (courseMatch(8).length > 0) true else false, //team teaching
        courseMatch(9),        //professornamemain
        if (courseMatch(10) == null) Some(courseMatch(10)) else None, //professornameadditional
        courseMatch(11).toInt, //credithours
        courseMatch(12).toInt, //coursehours
        (Map[DayOfWeek,CourseTime]() /: List(
          (courseMatch(13), List(courseMatch(14), courseMatch(15), courseMatch(16)),
            if (courseMatch(17) != null) courseMatch(17) else if (courseMatch(22) != null) courseMatch(22) else courseMatch(27)),
          (courseMatch(18), List(courseMatch(19), courseMatch(20), courseMatch(21)),
            if (courseMatch(22) == null) courseMatch(27) else courseMatch(22)),
          (courseMatch(23), List(courseMatch(24), courseMatch(25), courseMatch(26)), courseMatch(27)))) (
          (m, t) => t match {
            case (null, _, _) => m
            case t => m + (getDayOfWeek(t._1) -> new CourseTime(t._2, t._3))
          }),
        courseMatch(28).toInt, //currentlyenrolled
        courseMatch(29).toInt, //maximumenrolled
        if (courseMatch(30) == null) Some(courseMatch(30)) else None //note
      )
    }.toSeq
  }
}
