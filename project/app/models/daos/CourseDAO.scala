package models.daos

import java.util.UUID
import scala.concurrent.Future
import models._

//필요한건 들어야 하는 수업 추천!
//들어야 하는 수업 추천을 위해선 findBySubject
//
trait CourseDAO {
  def findById(id: UUID): Future[Option[Course]]

  def findByCodeDepartment(codePrefix: String, department: Department): Future[Seq[Course]]
}
