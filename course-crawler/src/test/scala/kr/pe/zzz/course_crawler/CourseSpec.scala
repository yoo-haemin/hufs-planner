package kr.pe.zzz.course_crawler

import org.scalatest._

class CourseSpec extends FlatSpec with Matchers {
  val eiccPage2017Source = scala.io.Source.fromFile("assets/2017-01-EICC.html")
  val eiccPage2017 = try eiccPage2017Source.mkString finally eiccPage2017Source.close()

  "Extracting Courses from 2017-01 EICC class" should "be of length 67" in {
    Course.fromSchoolCourseBody(eiccPage2017).length shouldEqual 67

    println(Course.fromSchoolCourseBody(eiccPage2017))
  }

}
