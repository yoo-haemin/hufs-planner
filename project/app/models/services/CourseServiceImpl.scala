package models.services

import javax.inject.Inject

import java.util.UUID
import java.time.Year

import models._
import models.daos.CourseDAO

import scala.concurrent.Future
import shared.Util.reverseEffect
import scala.concurrent.ExecutionContext.Implicits.global

class CourseServiceImpl @Inject() (
  courseDAO: CourseDAO,
  departmentService: DepartmentService
) extends CourseService {

  def findById(id: UUID): Future[Option[Course]] = courseDAO.findById(id)

  def find(year: Year, semester: Semester, codePrefix: String, department: Option[Department]): Future[Option[Course]] = for {
    courses <- courseDAO.find(Some(year), Some(semester), Some(codePrefix))
  } yield department match {
    case Some(dept) => courses.filter(_.departmentId == dept.id).headOption
    case None => courses.headOption
  }

  def find(year: Option[Year] = None, semester: Option[Semester] = None, codePrefix: Option[String] = None, codeSuffix: Option[String] = None): Future[Seq[Course]] = courseDAO.find(year, semester, codePrefix, codeSuffix)

  def findAllById(ids: Seq[UUID]): Future[Seq[Option[Course]]] =
    reverseEffect(ids.map(findById))

  def findBySubject(subject: Subject): Future[Seq[Course]] =
    courseDAO.findByCodeDepartment(subject.code, subject.department)
}
