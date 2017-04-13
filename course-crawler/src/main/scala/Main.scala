package kr.pe.zzz.course_crawler

import java.io.{ File, PrintWriter }
import play.api.libs.json.Json


object Main {
  def main(args: Array[String]): Unit = {
    val allCourses = for {
      year <- 2005 to 2016
      semester <- 1 to 4
    } yield {
      CourseAreaCrawler.toJson(year, semester) -> new PrintWriter(new File(s"assets/$year-0$semester.json"))
    }

    allCourses.foreach { case (Some(json), writer) =>
      writer.write(Json.prettyPrint(json))
    }
  }
}
