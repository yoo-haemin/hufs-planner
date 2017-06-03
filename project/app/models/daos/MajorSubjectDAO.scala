package models.daos

import java.util.UUID

import scala.concurrent.Future

import models.{ Major, Subject, Department }

trait MajorSubjectDAO {
  def findByMajor(id: UUID): Future[Seq[(Subject, Boolean)]]
  def findBySubject(code: String, department: Department): Future[Seq[(Major, Boolean)]]
}
