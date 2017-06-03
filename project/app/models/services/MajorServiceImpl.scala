package models.services

import javax.inject.Inject
import java.util.UUID

import models._
import models.daos._

import shared.Util.reverseEffect

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Handles majors (student side major)
 * Collection of functions to get majors
 */
class MajorServiceImpl @Inject() (
  majorDAO: MajorDAO,
  majorSubjectDAO: MajorSubjectDAO,
  userMajorDAO: UserMajorDAO,
  departmentDAO: DepartmentDAO
) extends MajorService {

  /**
   * Returns a matching Major with a matching name
   *
   * @param name The name of the Major, in Korean
   * @param majorType The type of the Major
   * @return The found major.
   */
  def find(name: String, majorType: MajorType): Future[Option[Major]] =
    majorDAO.findByName(name).map { majorSeq => majorSeq.filter(_.majorType == majorType).headOption }

  def find(name: String, majorType: MajorType, year: Short): Future[Option[Major]] =
    majorDAO.findByName(name).map { majorSeq => majorSeq.filter(m => m.majorType == majorType && m.year == year).headOption }

  /**
   * Returns a sequence of majors of a given user
   *  @param subject The user to search
   *  @return The sequence majors the user has
   */
  def findByUser(userID: UUID): Future[Seq[Major]] =
    for {
      ums <- userMajorDAO.allMajor(userID)
      ms <- reverseEffect(ums.map(um => majorDAO.findById(um.majorID)))
    } yield {
      ms.map {
        case Some(m) => m
        case None => throw new NoSuchElementException("User has an invalid major")
      }
    }

  /**
   * Returns a sequence of majors and the given subject's requiredness
   *
   *  @param subject The subject to search
   *  @return The sequence of pairs of matching majors and its requiredness
   */
  def findBySubject(subject: Subject): Future[Seq[(Major, Boolean)]] =
    majorSubjectDAO.findBySubject(subject.code, subject.department)

  /**
   * Returns a sequence of majors and the subject's requiredness of a given course
   *
   *  @param course The course to search
   *  @return The sequence of pairs of matching majors and its requiredness
   */
  def findByCourse(course: Course): Future[Seq[(Major, Boolean)]] =
    for {
      dept <- departmentDAO.findById(course.departmentId)
      ms <- majorSubjectDAO.findBySubject(course.codePrefix, dept)
    } yield {
      ms
    }

  def allOfType(): Future[Map[MajorType, Seq[(String, String)]]] = {
    majorDAO.all()
      .map {
        _.groupBy {
          case Major(_, _, _, majorType, _) => majorType
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
