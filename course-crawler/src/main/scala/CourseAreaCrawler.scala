package kr.pe.zzz.course_crawler

import scalaj.http._

object CourseAreaCrawler {
  /**
    *
    */
  def extractMajor(body: String): Option[Set[CourseDivision]] = {
    val majorCourseRegex = """<option value="(A.{2,4}_H1)"\s*(?: selected="")*>\s*&nbsp;\(서울\)&nbsp;-&nbsp;(.*)&nbsp;\((.*)\)&nbsp;""".r
    val majorFullRegex = """(?:<option value="(A.{2,4}_H1)"\s*(?: selected="")*>\s*&nbsp;\(서울\)&nbsp;-&nbsp;(.*)&nbsp;\((.*)\)&nbsp;\s*<\/option>\s*)+""".r

    majorFullRegex.findFirstIn(body).map { body =>
      majorCourseRegex.findAllMatchIn(body).toVector.map { courseMatch =>
        val details = courseMatch.subgroups.toList
        Major(details(0), details(1), details(2),

        )
      }.toSet
    }
  }

  /**
    *
    */
  def extractLiberalArts(body: String): Option[Set[CourseDivision]] = {
    val liberalArtsCourseRegex = """<option value {0,1}="(\d{2,3}[A-Z]{0,1}_H1)"\s*>\s*&nbsp;(.*)&nbsp;\s*<\/option>\s*""".r
    val liberalArtsFullRegex =  """(?:<option value {0,1}="(\d{2,3}[A-Z]{0,1}_H1)"\s*>\s*&nbsp;(.*)&nbsp;\s*<\/option>\s*)+""".r
    liberalArtsFullRegex.findFirstIn(body).map { body =>
      liberalArtsCourseRegex.findAllMatchIn(body).toVector.map { courseMatch =>
        val details = courseMatch.subgroups.toList
        LiberalArts(details(0), details(1), null)
      }.toSet
    }
  }
  //Define the url
  val coursePageUrl = "http://webs.hufs.ac.kr:8989/src08/jsp/lecture/LECTURE2020L.jsp"

  //Base params
  val courseFetchBaseParams = Map(
    "tab_lang" -> "K",
    "type" -> "",
    "ag_org_sect" -> "A", //
    "campus_sect" -> "H1"
  )

  //년도와 학기를 만든다
  // val yearSemesterParams = for {
  //   year <- 2008 to 2017
  //   semester <- 1 to 4
  // } yield courseFetchBaseParams ++ Map("ag_ledg_year" -> year.toString, "ag_ledg_sessn" -> semester.toString)

  def apply(year: Int, semester: Int): Option[Tuple2[Set[CourseDivision],Set[CourseDivision]]] = {
    val params = courseFetchBaseParams ++ Map("ag_ledg_year" -> year.toString, "ag_ledg_sessn" -> semester.toString)
    val body: String = Http(coursePageUrl).postForm.params(params).asString.body
    for {
      mj <- extractMajor(body)
      la <- extractLiberalArts(body)
    } yield (mj -> la)
  }
}

/*
  def createMajorParams(majorParams: Map[String,String]): Map[String,String] =  majorParams ++ Map(
    "gubun" -> "1" //, //전공을 선택
//    "ag_compt_fld_cd" -> "302_H1" //교양을 아무렇게나 정함
  )

  def createLiberalArtsParams(liberalArtsParams: Map[String,String]) = liberalArtsParams ++ Map(
    "gubun" -> "2" //,
//    "ag_crs_strct_cd" -> "AAR01_H1"
  )
 */
