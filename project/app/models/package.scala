package _root_

package object models {
  import java.time.DayOfWeek
  type CourseTime = Seq[(DayOfWeek, Seq[Int], String)]
}
