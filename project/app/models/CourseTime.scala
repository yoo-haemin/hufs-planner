package models

case class CourseTime(courseId: Int, time: Seq[(java.time.DayOfWeek, fun.lambda.coursecrawler.CourseTime)]) {
  def toMap: Map[java.time.DayOfWeek,fun.lambda.coursecrawler.CourseTime] = this.time.toMap
}
