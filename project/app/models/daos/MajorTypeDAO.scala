package models.daos

import scala.concurrent.Future
import models.MajorType

trait MajorTypeDAO {
  def findById(id: Int): Future[MajorType]

  def findByName(name: String): Future[MajorType]

  def all(): Future[Seq[MajorType]]

  def insert(MajorType: MajorType): Future[Int]

}
