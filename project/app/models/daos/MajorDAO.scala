package models.daos

import scala.concurrent.Future
import models.Major

trait MajorDAO {
  def findById(id: String): Future[Seq[Major]]

  def all(): Future[Seq[Major]]

  def insert(major: Major): Future[String]
}
