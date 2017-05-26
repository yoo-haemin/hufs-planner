package models

import java.time.DayOfWeek
import collection.immutable.SortedSet

case class CourseTime(
  id: java.util.UUID,
  value: Map[DayOfWeek, (SortedSet[Int], String)]
)
