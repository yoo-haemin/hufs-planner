package course_crawler

object CourseCrawlerData {
  def extractMajor(body: String) = {
    val majorCourseRegex = """<option value="(A.{2,4}_H1)"(?: selected="")*>\s*&nbsp;\(서울\)&nbsp;-&nbsp;(.*)&nbsp;\((.*)\)&nbsp;""".r
    val majorFullRegex = """(?:<option value="(A.{2,4}_H1)"(?: selected="")*>\s*&nbsp;\(서울\)&nbsp;-&nbsp;(.*)&nbsp;\((.*)\)&nbsp;\s*<\/option>\s*)+""".r

    majorFullRegex.findFirstIn(body).map { body =>
      majorCourseRegex.findAllMatchIn(body).toVector.map { courseMatch =>
        List("code", "name1", "name2").zip(courseMatch.subgroups).toMap
      }
    }
  }

  def extractLiberalArts(body: String) = {
    val liberalArtsCourseRegex = """<option value="(\d{2,3}[A-Z]{0,1}_H1)">\s*&nbsp;(.*)&nbsp;""".r
    val liberalArtsFullRegex =  """(?:<option value="(\d{2,3}[A-Z]{0,1}_H1)">\s*&nbsp;(.*)&nbsp;\s*<\/option>\s*)+""".r

    liberalArtsFullRegex.findFirstIn(body).map { body =>
      liberalArtsCourseRegex.findAllMatchIn(body).toVector.map { courseMatch =>
        List("code", "name1").zip(courseMatch.subgroups).toMap
      }
    }
  }
}
