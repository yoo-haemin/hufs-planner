package models.services

import javax.inject.Inject

import models.{ Major, MajorType }
import models.daos.MajorDAO

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Handles majors (student side major)
 */
class MajorServiceImpl @Inject() (majorDAO: MajorDAO) extends MajorService {

  /**
   * Returns a matching Major with a matching name
   *
   * @param name The name of the Major, in Korean
   * @param majorType The type of the Major
   * @return The found major.
   */
  def find(name: String, majorType: MajorType): Future[Major] =
    majorDAO.findByName(name).map { majorSeq => majorSeq.filter(_.majorType == majorType).head }

  def allOfType(): Future[Map[MajorType, Seq[(String, String)]]] = {
    majorDAO.all()
      .map {
        _.groupBy {
          case Major(id, nameKo, nameEn, majorType, year) => majorType
        }.map {
          case (majorType, majorseq) =>
            majorType ->
              majorseq.map {
                case Major(_, nameKo, nameEn, _, _) => nameKo -> nameEn.getOrElse("")
              }
        }
      }
  }
}
