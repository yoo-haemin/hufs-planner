package models.daos

import scala.concurrent.Future
import java.util.UUID
import models.Major

trait MajorDAO {
  def findById(id: UUID): Future[Seq[Major]]

  def all(): Future[Seq[Major]]

  def insert(major: Major): Future[UUID]
}
