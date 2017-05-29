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

  def allOfType(mt: MajorType): Future[Seq[(String, String)]] = {
    majorDAO.all().map { majors =>
      majors.filter {
        case Major(code, nameKo, nameEn, majorType, year) => majorType == mt
      }.map {
        case Major(code, nameKo, nameEn, majorType, year) =>
          nameKo -> (nameKo + "(" + nameEn.getOrElse("") + ")")
      }
    }
  }
}
