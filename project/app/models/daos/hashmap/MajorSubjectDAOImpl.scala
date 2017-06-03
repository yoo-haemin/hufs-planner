package models.daos.hashmap

import java.util.UUID
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import models._
import models.daos.{ MajorSubjectDAO, MajorDAO, SubjectDAO }

import shared.Util.reverseEffect

class MajorSubjectDAOImpl @Inject() (
  majorDAO: MajorDAO, subjectDAO: SubjectDAO) extends MajorSubjectDAO {

  /**
   * @param id The UUID of major
   * @return Sequence of pairs, subjects of the major -> required
   */
  def findByMajor(id: UUID): Future[Seq[(Subject, Boolean)]] = {
    val majorSubjects = MajorSubjectDAOImpl.majorSubjects.filter(_.majorID == id)
    majorSubjects.foldLeft(Future.successful(Seq[(Subject, Boolean)]())) { (acc, ms) =>
      (acc zip Future.successful(ms.subject)).map(p => p._1 :+ (p._2 -> ms.required))
    }
  }

  def findBySubject(code: String, department: Department): Future[Seq[(Major, Boolean)]] = {
    val majorSubject: Seq[MajorSubject] =
      MajorSubjectDAOImpl.majorSubjects.filter(
        ms => ms.subject.code == code && ms.subject.department == department).toSeq
    reverseEffect(
      majorSubject.map(ms => majorDAO.findById(ms.majorID) zip Future.successful(ms.required)))
      .map(_.map(p => p._1.get -> p._2))
  }
}

object MajorSubjectDAOImpl {
  //Sample Majors: Must be kept in sync with real sample majors
  val iel13Maj = UUID.fromString("5756959c-46c4-11e7-8db2-d413de251a16")
  val ji12Maj = UUID.fromString("5876d484-46c4-11e7-8db2-d413de251a16")
  val sc13Maj = UUID.fromString("58cbd8ec-46c4-11e7-8db2-d413de251a16")
  val sc12Maj = UUID.fromString("59192f06-46c4-11e7-8db2-d413de251a16")
  val la12Maj = UUID.fromString("1224380a-46e2-11e7-8db2-d413de251a16")
  val la13Maj = UUID.fromString("130aff94-46e2-11e7-8db2-d413de251a16")

  val free13Maj = UUID.fromString("9313c946-7fa9-4fe6-9e99-3d4a7b45058d")
  val free12Maj = UUID.fromString("c4211166-b7b4-451b-93bb-ac8cda7ca7a3")

  //Sample Departments: Must be kept in sync with real sample departments
  val ielDept = Department("AEAA1_H1", "국제통상학과", Campus.Seoul, Affiliation.Undergraduate)
  val journalDept = Department("ACDA1_H1", "언론정보전공", Campus.Seoul, Affiliation.Undergraduate)
  val softDept = Department("ATMB2_H1", "융복합소프트웨어전공", Campus.Seoul, Affiliation.Undergraduate)
  val laDept = Department("GYOYANG", "교양", Campus.Seoul, Affiliation.Undergraduate)

  //Sample MajorSubjects
  var majorSubjects = Set[MajorSubject](
    MajorSubject(iel13Maj, Subject("D01384", "International Investment Law", Some("IIL"), ielDept), false),
    MajorSubject(iel13Maj, Subject("D02369", "Law & Economics", Some("LNE"), ielDept), true),
    MajorSubject(iel13Maj, Subject("D01395", "Econometrics and Data Analysis", Some("ENDA"), ielDept), true),

    //All required for 13
    MajorSubject(sc13Maj, Subject("T05202", "컴퓨터프로그래밍2", Some("컴프2"), softDept), true),
    MajorSubject(sc13Maj, Subject("V44301", "컴퓨터구조", Some("컴구"), softDept), true),
    MajorSubject(sc13Maj, Subject("V44303", "자료구조", Some("자구"), softDept), true),
    MajorSubject(sc13Maj, Subject("T07403", "종합설계", Some("Capstone Design"), softDept), true),

    //All not required for 12
    MajorSubject(sc12Maj, Subject("T05202", "컴퓨터프로그래밍2", Some("컴프2"), softDept), false),
    MajorSubject(sc12Maj, Subject("V44301", "컴퓨터구조", Some("컴구"), softDept), false),
    MajorSubject(sc12Maj, Subject("V44303", "자료구조", Some("자구"), softDept), false),
    MajorSubject(sc12Maj, Subject("T07403", "종합설계", Some("Capstone Design"), softDept), false),

    MajorSubject(ji12Maj, Subject("C04533", "미디어심리학", Some("Media Psychology"), journalDept), true),
    MajorSubject(ji12Maj, Subject("C04318", "언론정보윤리와법", Some("Journalism Ethics and Law"), journalDept), false),
    MajorSubject(ji12Maj, Subject("C04526", "미디어와경제", Some("Media and Economy"), journalDept), true),

    MajorSubject(la12Maj, Subject("A12345", "교양영어", None, laDept), false),
    MajorSubject(la12Maj, Subject("B45678", "인터넷의이해", None, laDept), false),
    MajorSubject(la12Maj, Subject("C54321", "씐나는교양", None, laDept), false),

    MajorSubject(la13Maj, Subject("A12345", "교양영어", None, laDept), false),
    MajorSubject(la13Maj, Subject("B45678", "인터넷의이해", None, laDept), false),
    MajorSubject(la13Maj, Subject("C54321", "씐나는교양", None, laDept), false),

    //All Major subjects are also in freecourse maj
    MajorSubject(free13Maj, Subject("D01384", "International Investment Law", Some("IIL"), ielDept), false),
    MajorSubject(free13Maj, Subject("D02369", "Law & Economics", Some("LNE"), ielDept), true),
    MajorSubject(free13Maj, Subject("D01395", "Econometrics and Data Analysis", Some("ENDA"), ielDept), true),
    MajorSubject(free13Maj, Subject("T05202", "컴퓨터프로그래밍2", Some("컴프2"), softDept), true),
    MajorSubject(free13Maj, Subject("V44301", "컴퓨터구조", Some("컴구"), softDept), true),
    MajorSubject(free13Maj, Subject("V44303", "자료구조", Some("자구"), softDept), true),
    MajorSubject(free13Maj, Subject("T07403", "종합설계", Some("Capstone Design"), softDept), true),
    MajorSubject(free13Maj, Subject("C04533", "미디어심리학", Some("Media Psychology"), journalDept), true),
    MajorSubject(free13Maj, Subject("C04318", "언론정보윤리와법", Some("Journalism Ethics and Law"), journalDept), false),
    MajorSubject(free13Maj, Subject("C04526", "미디어와경제", Some("Media and Economy"), journalDept), true),

    //All major subjects are also in freecourse maj
    MajorSubject(free12Maj, Subject("D01384", "International Investment Law", Some("IIL"), ielDept), false),
    MajorSubject(free12Maj, Subject("D02369", "Law & Economics", Some("LNE"), ielDept), true),
    MajorSubject(free12Maj, Subject("D01395", "Econometrics and Data Analysis", Some("ENDA"), ielDept), true),
    MajorSubject(free12Maj, Subject("T05202", "컴퓨터프로그래밍2", Some("컴프2"), softDept), true),
    MajorSubject(free12Maj, Subject("V44301", "컴퓨터구조", Some("컴구"), softDept), true),
    MajorSubject(free12Maj, Subject("V44303", "자료구조", Some("자구"), softDept), true),
    MajorSubject(free12Maj, Subject("T07403", "종합설계", Some("Capstone Design"), softDept), true),
    MajorSubject(free12Maj, Subject("C04533", "미디어심리학", Some("Media Psychology"), journalDept), true),
    MajorSubject(free12Maj, Subject("C04318", "언론정보윤리와법", Some("Journalism Ethics and Law"), journalDept), false),
    MajorSubject(free12Maj, Subject("C04526", "미디어와경제", Some("Media and Economy"), journalDept), true)

  )
}
