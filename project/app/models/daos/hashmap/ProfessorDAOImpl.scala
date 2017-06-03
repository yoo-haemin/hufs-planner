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
  var professors = Set[Professor](
    Professor(UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), "교수님 1", Some("Professor 1")),
    Professor(UUID.fromString("14194feb-46e2-11e7-8db2-d413de251a16"), "교수님 2", Some("Professor 2")),
    Professor(UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), "교수님 3", Some("Professor 3")),
    Professor(UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), "교수님 4", Some("Professor 4")),
    Professor(UUID.fromString("14fda29b-46e2-11e7-8db2-d413de251a16"), "교수님 5", Some("Professor 5")),
    Professor(UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), "교수님 6", Some("Professor 6")),
    Professor(UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), "교수님 7", Some("Professor 7"))
  )
}
