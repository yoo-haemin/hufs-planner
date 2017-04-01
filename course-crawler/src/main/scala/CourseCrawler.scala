package course_crawler

import scalaj.http._
import CourseCrawlerData._

import org.json4s._
import org.json4s.native.Serialization._
import org.json4s.native.Serialization
implicit val formats = Serialization.formats(NoTypeHints)

trait CourseArea
case class Major(name: String) extends CourseArea
case class LiberalArts(name: String) extends CourseArea

object CourseCrawler extends App {
  //Define the url
  val coursePageUrl = "http://webs.hufs.ac.kr:8989/src08/jsp/lecture/LECTURE2020L.jsp"

  //Base params
  val courseFetchBaseParams = Map(
    "tab_lang" -> "K",
    "type" -> "",
    "ag_org_sect" -> "A", //
    "campus_sect" -> "H1"
  )

  val yearSemesterParams = for {
    year <- 2008 to 2017
    semester <- 1 to 4
  } yield courseFetchBaseParams ++ Map("ag_ledg_year" -> year.toString, "ag_ledg_sessn" -> semester.toString)

  def getMajorList() = {

  }

  yearSemesterParams.map { params =>
    ((params.get("ag_ledg_year") -> params.get("ag_ledg_sessn")) -> Http(coursePageUrl).postForm.params(params).asString.body)
  }. map { data =>
    (data._1 -> (extractMajor(data._2) -> extractLiberalArts(data._2)))
  }


  val majorFetchParams = courseFetchBaseParams ++ Map(
    "gubun" -> "1",
    "ag_compt_fld_cd" -> "302_H1"
  )

  val liberalArtsFetchParams = courseFetchBaseParams ++ Map(
    "gubun" -> "1",
    "ag_crs_strct_cd" -> "AAR01_H1"
  )
}
