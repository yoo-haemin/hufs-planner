package models

import java.util.UUID
import java.time.DayOfWeek
import collection.immutable.SortedSet

case class CourseTime(
  id: UUID,
  value: Map[DayOfWeek, (SortedSet[Int], String)]
)
