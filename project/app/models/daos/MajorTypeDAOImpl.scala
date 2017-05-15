package models.daos

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider

import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import models.MajorType

class MajorTypeDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends MajorTypeDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val majorTypes = TableQuery[MajorTypesTable]

  def findById(id: Int): Future[MajorType] =
    db.run(majorTypes.filter(_.id === id).result.head)

  def findByName(name: String): Future[MajorType] =
    db.run(majorTypes.filter(_.name === name).result.head)

  def all(): Future[Seq[MajorType]] =
    db.run(majorTypes.result)

  def insert(MajorType: MajorType): Future[Int] =
    db.run(majorTypes returning majorTypes.map(_.id) += MajorType)

  class MajorTypesTable(tag: Tag) extends Table[MajorType](tag, "major_types") {
    def name = column[String]("year")
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def * = (id, name) <> (MajorType.tupled, MajorType.unapply _)
  }
}
