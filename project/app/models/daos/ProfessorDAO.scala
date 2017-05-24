package models.daos

import scala.concurrent.Future
import java.util.UUID
import models.Professor

trait ProfessorDAO {
  def findByName(name: String): Future[Seq[Professor]]

  def all(): Future[Seq[Professor]]

  def insert(Professor: Professor): Future[UUID]
}
