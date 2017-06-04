package models.services

import java.util.UUID
import java.time.Year
import javax.inject.Inject

import models._
import models.daos._
import models.MajorType._
import shared.Util.reverseEffect
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class UserCourseServiceImpl @Inject() (
  userCourseDAO: UserCourseDAO,
  majorSubjectDAO: MajorSubjectDAO,
  courseService: CourseService,
  majorService: MajorService,
  userService: UserService,
  userMajorService: UserMajorService
) extends UserCourseService {

  /**
   * Retrieves courses of a specified userID.
   *
   * @param userID The userID to retrieve a user.
   * @param future the type of course to return
   * @return The retrieved courses
   */
  def allUserCourse(userID: UUID, future: Boolean = false): Future[Seq[UserCourse]] =
    userCourseDAO.allCourse(userID, future)

  def allCourse(userID: UUID, future: Boolean = false): Future[Seq[(Option[Course], Score)]] =
    for {
      userCourses <- allUserCourse(userID)
      userCourseInfo <- courseService.findAllById(userCourses.map(_.courseID))
      scores = userCourses.map(_.score)
    } yield userCourseInfo zip scores

  def perSemester(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), Seq[(Option[Course], Score)]]] =
    allCourse(userID, future).map { ac =>
      ac
        .groupBy {
          case (Some(c), s) => (c.year -> c.semester)
          case (None, _) => throw new NoSuchElementException("User registered an unknown course")
        }
    }

  def perSemesterAvg(userID: UUID, future: Boolean = false): Future[Map[(Year, Semester), (Int, Double)]] =
    perSemester(userID, future).map { ac =>
      ac
        .map {
          case ((yr, sem), seq) =>
            val credits = seq.map(_._1.get.creditHours.toInt)

            (yr -> sem) -> (credits.sum -> Score.avg(seq.map(_._2) zip credits))
        }
    }

  def perMajor(userID: UUID, future: Boolean = false): Future[Map[Major, Seq[(Option[Course], Score)]]] =
    for {
      allCourses <- allCourse(userID, future) //all courses user took
      userMajor <- majorService.findByUser(userID) //all major user has
      allCoursesWithMajor <- reverseEffect(
        allCourses.map {
          case (Some(course), score) =>
            (majorService.findByCourse(course) zip
              (Future.successful(Some(course)) zip Future.successful(score)))
          case (None, _) => throw new NoSuchElementException("User registered an unknown course")
        })
      userMajorCourse = allCoursesWithMajor.map { allCwM =>
        val majorPair: Seq[(Major, Boolean)] = allCwM._1
        val majors: Seq[Major] = majorPair.map(_._1)
        majors.toSet.intersect(userMajor.toSet).head -> allCwM._2
      }
    } yield userMajorCourse
      .groupBy(_._1)
      .map {
        case (maj, seq) =>
          maj -> seq.map {
            case (maj, (cOpt, score)) =>
              cOpt -> score
          }
      }.toMap

  def perMajorAvg(userID: UUID, future: Boolean = false): Future[Map[Major, (Int, Double)]] =
    perMajor(userID, future).map { ac =>
      ac
        .map {
          case (major, seq) =>
            val credits = seq.map(_._1.get.creditHours.toInt)

            major -> (credits.sum -> Score.avg(seq.map(_._2) zip credits))
        }
    }

  /**
   * Course recommendation methods
   */
  def recommendedCourse(userID: UUID, yearSemester: (Year, Semester)): Future[Map[MajorType, Seq[Course]]] = for {
    //Find the majors from users
    majors <- majorService.findByUser(userID)

    user <- userService.retrieve(userID).map(_.get)

    //Find already taken courses
    takenCourses: Seq[(Option[Course], Score)] <- allCourse(userID)

    passedCourses = takenCourses.filter { takenCourse: (Option[Course], Score) =>
      takenCourse match {
        case (Some(c), s) if s.passed => true
        case _ => false
      }
    } map {
      case (courseOpt: Option[Course], score) => courseOpt.get
    }

    //Find subjects from majors by user
    majorSubjects: Seq[(MajorType, Seq[(Subject, Boolean)])] <- reverseEffect(
      majors.filter {
        m => m.majorType == FirstMajor || m.majorType == SecondMajor
      } map {
        m => Future.successful(m.majorType) zip majorSubjectDAO.findByMajor(m.id)
      })

    //Filter unfulfilled subjects
    unFulfilledSubjects: Seq[(MajorType, Seq[Subject])] = majorSubjects.map {
      case (mt, subjects) =>
        mt -> (subjects.filter {
          case (sbj, req) =>
            //required and not over user's next semester OR
            (req && sbj.recommendedYear.getOrElse(0) < (user.semester + 1) / 2) ||
              //subject has same recommended year as the user's next semester
              sbj.recommendedYear.getOrElse(0) == ((user.semester + 1) / 2)
        } map { case (sbj, req) => sbj })
    }

    //Filter by subject
    recommendedCourseSeq <- reverseEffect(
      unFulfilledSubjects.map {
        case (mt, seq) =>
          Future.successful(mt) zip
            reverseEffect(seq.map { sbj => courseService.findBySubject(sbj) })
            .map(_.flatten.filterNot(c => passedCourses.exists(pc => pc.codePrefix == c.codePrefix)))
      })

    //Filter by time
    recommendedCourse = recommendedCourseSeq.map {
      case (mt, seq) =>
        mt -> seq.filter(c => c.year == yearSemester._1 && c.semester == yearSemester._2)
    }.toMap

  } yield recommendedCourse

  def retakeCourse(userID: UUID, yearSemester: (Year, Semester)): Future[Map[MajorType, Seq[Course]]] =
    for {
      //Find the majors from users
      majors <- majorService.findByUser(userID)

      user <- userService.retrieve(userID).map(_.get)

      //Find already taken courses
      takenCourses: Seq[(Option[Course], Score)] <- allCourse(userID)

      retakableCourses = takenCourses.filter { takenCourse: (Option[Course], Score) =>
        takenCourse match {
          case (Some(c), s) if s.retakable => true
          case _ => false
        }
      } map {
        case (courseOpt: Option[Course], score) => courseOpt.get
      }

      //Find subjects from majors by user
      majorSubjects: Seq[(MajorType, Seq[(Subject, Boolean)])] <- reverseEffect(
        majors.filter {
          m => m.majorType == FirstMajor || m.majorType == SecondMajor
        } map {
          m => Future.successful(m.majorType) zip majorSubjectDAO.findByMajor(m.id)
        })

      //Filter unfulfilled subjects
      unFulfilledSubjects: Seq[(MajorType, Seq[Subject])] = majorSubjects.map {
        case (mt, subjects) =>
          mt -> subjects.map { case (sbj, req) => sbj }
      }

      //Filter by subject
      recommendedCourseSeq <- reverseEffect(
        unFulfilledSubjects.map {
          case (mt, seq) =>
            Future.successful(mt) zip
              reverseEffect(seq.map { sbj => courseService.findBySubject(sbj) })
              .map(_.flatten.filter(c => retakableCourses.exists(pc => pc.codePrefix == c.codePrefix)))
        })

      //Filter by time
      recommendedCourse = recommendedCourseSeq.map {
        case (mt, seq) =>
          mt -> seq.filter(c => c.year == yearSemester._1 && c.semester == yearSemester._2)
      }.toMap

    } yield recommendedCourse

  def otherMajorCourse(userID: UUID, yearSemester: (Year, Semester)): Future[Map[MajorType, Seq[Course]]] = for {
    //Find the majors from users
    majors <- majorService.findByUser(userID)

    user <- userService.retrieve(userID).map(_.get)

    //Find already taken courses
    takenCourses: Seq[(Option[Course], Score)] <- allCourse(userID)

    notTakenCourse = takenCourses.filter { takenCourse: (Option[Course], Score) =>
      takenCourse match {
        case (Some(c), s) => true
        case _ => false
      }
    } map {
      case (courseOpt: Option[Course], score) => courseOpt.get
    }

    //Find subjects from majors by user
    majorSubjects: Seq[(MajorType, Seq[(Subject, Boolean)])] <- reverseEffect(
      majors.filter {
        m => m.majorType == FirstMajor || m.majorType == SecondMajor
      } map {
        m => Future.successful(m.majorType) zip majorSubjectDAO.findByMajor(m.id)
      })

    //Filter unfulfilled subjects
    unFulfilledSubjects: Seq[(MajorType, Seq[Subject])] = majorSubjects.map {
      case (mt, subjects) =>
        mt -> (subjects.filter {
          case (sbj, req) =>
            sbj.recommendedYear.getOrElse(0) < (user.semester + 1) / 2
        } map { case (sbj, req) => sbj })
    }

    //Filter by subject
    recommendedCourseSeq <- reverseEffect(
      unFulfilledSubjects.map {
        case (mt, seq) =>
          Future.successful(mt) zip
            reverseEffect(seq.map { sbj => courseService.findBySubject(sbj) })
            .map(courses => courses.flatten.filterNot(c => notTakenCourse.exists(pc => pc.codePrefix == c.codePrefix)))
      })

    //Filter by time
    recommendedCourse = recommendedCourseSeq.map {
      case (mt, seq) =>
        mt -> seq.filter(c => c.year == yearSemester._1 && c.semester == yearSemester._2)
    }.toMap

  } yield recommendedCourse

  def save(userID: UUID, courseID: UUID, score: Int, retake: Boolean = false, future: Boolean = false): Future[UserCourse] =
    userCourseDAO.save(UserCourse(userID, courseID, score, retake, future))

  def removeAll(userID: UUID, future: Boolean = false): Future[Unit] =
    allUserCourse(userID, future).map(ucs => ucs.map(userCourseDAO.remove(_)))
}
