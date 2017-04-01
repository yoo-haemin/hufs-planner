package course_crawler

import org.scalatest._
import CourseCrawlerData._

class CourseCrawlerDataSpec extends FlatSpec with Matchers {
  val eiccPage2017Source = scala.io.Source.fromFile("assets/2017-01-EICC.html")
  val eiccPage2017 = try eiccPage2017Source.mkString finally eiccPage2017Source.close()

  "Extracting Major from 2017-1" should "be of length 68" in {
    extractMajor(eiccPage2017).get.length shouldEqual 68
    // println(extractMajor(eiccPage2017))
  }

  "Extracting Liberal Arts from 2017-1" should "be of length 16" in {
    extractLiberalArts(eiccPage2017).get.length shouldEqual 16
  }


}
