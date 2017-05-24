package models.daos

import scala.concurrent.Future
import java.util.UUID

import models.CourseArea

trait CourseAreaDAO {
  def findByValue(value: String): Future[CourseArea]

  def all(): Future[Seq[CourseArea]]

  def insert(CourseArea: CourseArea): Future[UUID]
}
