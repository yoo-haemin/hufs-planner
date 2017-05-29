package models.daos.hashmap

import javax.inject.Inject
import java.time.Year
import scala.concurrent.Future
import models.Major
import models.MajorType._
import models.daos.MajorDAO

class MajorDAOImpl @Inject() extends MajorDAO {
  def findById(id: String): Future[Seq[Major]] =
    Future.successful(MajorDAOImpl.majors.filter(_.id == id).toSeq)

  def findByName(name: String): Future[Seq[Major]] =
    Future.successful(MajorDAOImpl.majors.filter(m => m.nameKo == name || m.nameEn == name).toSeq)

  def all(): Future[Seq[Major]] =
    Future.successful(MajorDAOImpl.majors.toSeq)

  def insert(major: Major): Future[String] = {
    Future.successful {
      MajorDAOImpl.majors = MajorDAOImpl.majors + major
      major.id
    }
  }
}

object MajorDAOImpl {
  var majors = Set[Major](
    Major(
      "AEAA1_H1", "국제통상학과", Some("Department of International  Economics and Law"),
      FirstMajor, Year.of(2013)
    ),
    Major(
      "ACDA1_H1", "언론·정보전공", Some("Journalism & Information"),
      FirstMajor, Year.of(2012)
    ),
    Major(
      "ATMB2_H1", "융복합소프트웨어전공", Some("Software Convergence Major"),
      SecondMajor, Year.of(2013)
    ),
    Major(
      "ATMB2_H1", "융복합소프트웨어전공", Some("Software Convergence Major"),
      SecondMajor, Year.of(2012)
    )
  )
}
