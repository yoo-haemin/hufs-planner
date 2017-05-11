package models

case class CourseTime (courseId: Int, time: Seq[(java.time.DayOfWeek, Seq[Int], String)])
