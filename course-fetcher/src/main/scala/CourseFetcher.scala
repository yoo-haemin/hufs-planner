package course_fetcher

import scalaj.http._
import CourseFetcherData._

object CourseFetcher extends App {
  val baseRequest: HttpRequest =
    Http("http://webs.hufs.ac.kr:8989/src08/jsp/lecture/LECTURE2020L.jsp")
      .postForm

  val majorCourseRaw: Map[String, String] = (for {
    c <- majorCourseCategory
  } yield (c._2 -> baseRequest.params(majorFetchParams + ("ag_crs_strct_cd" -> c._1)).asString.body))

  val liberalArtsCourseRaw = (for {
    c <- liberalArtsCourseCategory
  } yield (c._2 -> baseRequest.params(liberalArtsFetchParams + ("ag_compt_fld_cd" -> c._1)).asString.body))

  val majorCourses: Map[String, Iterator[Course]] = majorCourseRaw.map {
    raw => (raw._1 -> courseRegex
      .findAllMatchIn(raw._2)
      .map(m => m.subgroups.map(x => Option(x)))
      .map(Course.newCourse(_)))
  }

  val liberalArtsCourses = liberalArtsCourseRaw.map {
    raw => (raw._1 -> courseRegex
      .findAllMatchIn(raw._2)
      .map(m => m.subgroups.map(x => Option(x)))
      .map(Course.newCourse(_)))
  }

  majorCourses foreach {
    case (str, courses) => {
      print(s""""$str" -> Vector(""")
      courses foreach { c =>  print(c + ",") }
      println(")")
    }
  }

  liberalArtsCourses foreach {
    case (str, courses) => {
      print(s""""$str" -> Vector(""")
      courses foreach { c =>  print(c + ",") }
      println(")")
    }
  }
}
