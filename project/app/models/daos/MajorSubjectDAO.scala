package models.daos

import java.util.UUID

import scala.concurrent.Future

import models.Subject

trait MajorSubjectDAO {
  def findByMajor(id: UUID): Future[Seq[(Subject, Boolean)]]
}
