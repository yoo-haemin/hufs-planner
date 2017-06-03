package models.daos.hashmap

import javax.inject.Inject
import java.time.Year
import java.util.UUID
import scala.concurrent.Future
import models.Major
import models.MajorType._
import models.daos.MajorDAO

class MajorDAOImpl @Inject() extends MajorDAO {
  def findById(id: UUID): Future[Option[Major]] =
    Future.successful(MajorDAOImpl.majors.filter(_.id == id).headOption)

  def findByName(name: String): Future[Seq[Major]] =
    Future.successful(
      MajorDAOImpl.majors.filter(m => m.nameKo == name || m.nameEn.getOrElse("") == name).toSeq)

  def all(): Future[Seq[Major]] =
    Future.successful(MajorDAOImpl.majors.toSeq)

  def insert(major: Major): Future[String] =
    Future.successful {
      MajorDAOImpl.majors = MajorDAOImpl.majors + major
      major.nameKo
    }
}

object MajorDAOImpl {
  var majors = Set[Major](
    Major(
      UUID.fromString("5756959c-46c4-11e7-8db2-d413de251a16"), "국제통상학과", Some("Department of International Economics and Law"),
      FirstMajor, Year.of(2013)
    ),
    Major(
      UUID.fromString("5876d484-46c4-11e7-8db2-d413de251a16"), "언론정보전공", Some("Journalism & Information"),
      FirstMajor, Year.of(2012)
    ),
    Major(
      UUID.fromString("58cbd8ec-46c4-11e7-8db2-d413de251a16"), "융복합소프트웨어전공", Some("Software Convergence Major"),
      SecondMajor, Year.of(2013)
    ),
    Major(
      UUID.fromString("59192f06-46c4-11e7-8db2-d413de251a16"), "융복합소프트웨어전공", Some("Software Convergence Major"),
      SecondMajor, Year.of(2012)
    ),
    Major(
      UUID.fromString("1224380a-46e2-11e7-8db2-d413de251a16"), "교양", Some("Liberal Arts"),
      LiberalArts, Year.of(2012)
    ),
    Major(
      UUID.fromString("130aff94-46e2-11e7-8db2-d413de251a16"), "교양", Some("Liberal Arts"),
      LiberalArts, Year.of(2013)
    ),
    Major(
      UUID.fromString("9313c946-7fa9-4fe6-9e99-3d4a7b45058d"), "자선", Some("Free Choice"),
      FreeCourse, Year.of(2013)
    ),
    Major(
      UUID.fromString("c4211166-b7b4-451b-93bb-ac8cda7ca7a3"), "자선", Some("Free Choice"),
      FreeCourse, Year.of(2012)
    )
  )
}
