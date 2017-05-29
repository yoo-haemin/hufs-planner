package models.daos.hashmap

import javax.inject.Inject

import scala.concurrent.Future

import java.util.UUID

import models.Professor

import models.daos.ProfessorDAO

class ProfessorDAOImpl @Inject() extends ProfessorDAO {
  def findByName(name: String): Future[Seq[Professor]] =
    Future.successful(ProfessorDAOImpl.professors.filter(p => p.name1 == name || p.name2 == name).toSeq)

  def all(): Future[Seq[Professor]] =
    Future.successful(ProfessorDAOImpl.professors.toSeq)

  def insert(professor: Professor): Future[UUID] =
    Future.successful {
      ProfessorDAOImpl.professors = ProfessorDAOImpl.professors + professor
      professor.id
    }
}

object ProfessorDAOImpl {
  var professors = Set[Professor]()
}
