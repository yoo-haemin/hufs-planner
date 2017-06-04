package models.daos

import java.util.UUID
import java.time.Year
import scala.concurrent.Future
import models._

//필요한건 들어야 하는 수업 추천!
//들어야 하는 수업 추천을 위해선 findBySubject
//
trait CourseDAO {
  def findById(id: UUID): Future[Option[Course]]

  //TODO: move this up and create more base methods, this one is for service
  def find(year: Option[Year] = None, semester: Option[Semester] = None, codePrefix: Option[String] = None, codeSuffix: Option[String] = None): Future[Seq[Course]]

  def findByCodeDepartment(codePrefix: String, department: Department): Future[Seq[Course]]
}
