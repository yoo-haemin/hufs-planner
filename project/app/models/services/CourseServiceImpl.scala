package models.services

import javax.inject.Inject

import java.util.UUID

import models._
import models.daos.CourseDAO

import scala.concurrent.Future
import shared.Util.reverseEffect

class CourseServiceImpl @Inject() (courseDAO: CourseDAO) extends CourseService {

  def findById(id: UUID): Future[Option[Course]] = courseDAO.findById(id)

  def findAllById(ids: Seq[UUID]): Future[Seq[Option[Course]]] = {
    val courseFuture = ids.map(findById)
    reverseEffect(courseFuture)
  }

  def findBySubject(subject: Subject): Future[Seq[Course]] = courseDAO.findByCodeDepartment(subject.code, subject.department)
}
