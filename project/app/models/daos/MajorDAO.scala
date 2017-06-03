package models.daos

import java.util.UUID
import scala.concurrent.Future
import models.Major

trait MajorDAO {
  def findById(id: UUID): Future[Option[Major]]

  def findByName(name: String): Future[Seq[Major]]

  def all(): Future[Seq[Major]]

  def insert(major: Major): Future[String]
}
