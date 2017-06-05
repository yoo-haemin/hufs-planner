package models.daos.hashmap

import javax.inject.Inject

import scala.concurrent.Future

import models.{ Subject, Department, Campus, Affiliation }
import models.daos.SubjectDAO

class SubjectDAOImpl @Inject() extends SubjectDAO {
  def findByDepartmentId(id: String): Future[Seq[Subject]] =
    Future.successful(SubjectDAOImpl.subjects.filter(_.department.name == id).toSeq)

  def findByCode(code: String): Future[Seq[Subject]] =
    Future.successful(SubjectDAOImpl.subjects.filter(_.code == code).toSeq)

  def findByName(name: String): Future[Seq[Subject]] =
    Future.successful(
      SubjectDAOImpl.subjects.filter(
      s => s.name1 == name || s.name2.map(_ == name).getOrElse(false)).toSeq)

  def all(): Future[Seq[Subject]] =
    Future.successful(SubjectDAOImpl.subjects.toSeq)

  def insert(subject: Subject): Future[String] =
    Future.successful {
      SubjectDAOImpl.subjects = SubjectDAOImpl.subjects + subject
      subject.code
    }
}

/*
 case class Subject(
 code: String,
 name: String,
 department: Department)
*/
object SubjectDAOImpl {
  val ielDept = Department("AEAA1_H1", "국제통상학과", Campus.Seoul, Affiliation.Undergraduate)
  val journalDept = Department("ACDA1_H1", "언론정보전공", Campus.Seoul, Affiliation.Undergraduate)
  val softDept = Department("ATMB2_H1", "융복합소프트웨어전공", Campus.Seoul, Affiliation.Undergraduate)
  val laDept = Department("GYOYANG", "미네르바교양대학", Campus.Seoul, Affiliation.Undergraduate)

  var subjects = Set[Subject](
    //Predefined Subjects
    //IEL
    Subject("D01384", "International Investment Law", Some("IIL"), ielDept, Some(1)),
    Subject("D02369", "Law & Economics", Some("LNE"), ielDept, Some(1)),

    Subject("D013951", "Econometrics and Data Analysis", Some("ENDA"), ielDept, Some(3)),

    //Software
    Subject("T05202", "컴퓨터프로그래밍2", Some("컴프2"), softDept, Some(2)),
    Subject("V44301", "컴퓨터구조", Some("컴구"), softDept, Some(2)),
    Subject("V44303", "자료구조", Some("자구"), softDept, Some(3)),

    Subject("T07403", "종합설계", Some("Capstone Design"), softDept, Some(1)),

    //Journalism
    Subject("C04533", "미디어심리학", Some("Media Psychology"), journalDept, Some(1)),
    Subject("C04318", "언론정보윤리와법", Some("Journalism Ethics and Law"), journalDept, Some(1)),

    Subject("C04526", "미디어와경제", Some("Media and Economy"), journalDept, Some(4)),

    //Liberal Arts
    Subject("A12345", "교양영어", None, laDept, None),
    Subject("B45678", "인터넷의이해", None, laDept, None),
    Subject("C54321", "씐나는교양", None, laDept, None)
  )
}
