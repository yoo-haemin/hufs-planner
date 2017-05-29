package models.daos.hashmap

import javax.inject.Inject

import scala.concurrent.Future

import java.util.UUID

import models.Subject
import models.daos.SubjectDAO

class SubjectDAOImpl @Inject() extends SubjectDAO {
  def findByDepartmentId(id: String): Future[Subject] =
    Future.successful(SubjectDAOImpl.subjects.filter(_.departmentId == id).head)

  def findByName(name: String): Future[Subject] =
    Future.successful(SubjectDAOImpl.subjects.filter(_.name == name).head)

  def all(): Future[Seq[Subject]] =
    Future.successful(SubjectDAOImpl.subjects.toSeq)

  def insert(subject: Subject): Future[UUID] =
    Future.successful {
      SubjectDAOImpl.subjects = SubjectDAOImpl.subjects + subject
      subject.id
    }
}

object SubjectDAOImpl {
  var subjects = Set[Subject]()
}
