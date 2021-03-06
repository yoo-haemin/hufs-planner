package models.daos.hashmap

import javax.inject.Inject
import java.time.Year
import java.util.UUID
import scala.concurrent.Future
import models._
import models.Semester
import models.daos.CourseDAO

//import scala.concurrent.ExecutionContext.Implicits.global

class CourseDAOImpl @Inject() extends CourseDAO {
  def findById(id: UUID): Future[Option[Course]] =
    Future.successful(CourseDAOImpl.courses.filter(_.id == id).headOption)

  def find(
    year: Option[Year] = None,
    semester: Option[Semester] = None,
    codePrefix: Option[String] = None,
    codeSuffix: Option[String] = None
  ): Future[Seq[Course]] = Future.successful {
    (CourseDAOImpl.courses.filter { c => if (year.isEmpty) true else c.year.getValue == year.get.getValue } intersect
      CourseDAOImpl.courses.filter { c => if (year.isEmpty && semester.isEmpty) true else c.semester.toInt == semester.get.toInt } intersect
      CourseDAOImpl.courses.filter { c => if (codePrefix.isEmpty) true else c.codePrefix == codePrefix.get } intersect
      CourseDAOImpl.courses.filter { c => if (codeSuffix.isEmpty) true else c.codeSuffix == codeSuffix.get }).toSeq
  }

  def findByCodeDepartment(codePrefix: String, department: Department): Future[Seq[Course]] =
    Future.successful {
      CourseDAOImpl.courses.filter(
        c => c.codePrefix == codePrefix && c.departmentId == department.id
      ).toSeq
    }
}

object CourseDAOImpl {
  import scala.language.implicitConversions
  implicit def intToYear(i: Int): Year = Year.of(i)

  var courses = Set[Course](
    Course(UUID.fromString("16df05fa-46e2-11e7-8db2-d413de251a16"), "D01384", "101", "AEAA1_H1", 2016, 3, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("158a1799-48ee-4612-a5fb-e81811844e1a"), "International Investment Law", Some("IIL"), 3, 3),
    Course(UUID.fromString("172f36c4-46e2-11e7-8db2-d413de251a16"), "D02369", "101", "AEAA1_H1", 2016, 3, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("61c6150f-efb5-4d6e-8654-b23c3655f38a"), "Law & Economics", Some("LNE"), 3, 3),
    Course(UUID.fromString("177df62c-46e2-11e7-8db2-d413de251a16"), "D01395", "101", "AEAA1_H1", 2016, 3, UUID.fromString("14194feb-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("5bac8e39-992c-4486-ae89-5e4cfbc18f80"), "Econometrics and Data Analysis", Some("ENDA"), 3, 3),
    Course(UUID.fromString("17d5b1f6-46e2-11e7-8db2-d413de251a16"), "T05202", "101", "ATMB2_H1", 2016, 3, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f061eeed-1d56-487a-a593-0be34fc04200"), "컴퓨터프로그래밍2", Some("컴프2"), 3, 3),
    Course(UUID.fromString("1826ba61-46e2-11e7-8db2-d413de251a16"), "V44301", "101", "ATMB2_H1", 2016, 3, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("fe467d4f-fcc5-4da9-9d90-59011a31cf93"), "컴퓨터구조", Some("컴구"), 3, 3),
    Course(UUID.fromString("1872168c-46e2-11e7-8db2-d413de251a16"), "V44303", "101", "ATMB2_H1", 2016, 3, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("801d4734-4aa6-45c7-8c63-3e7c518eec0c"), "자료구조", Some("자구"), 3, 3),
    Course(UUID.fromString("18c51b45-46e2-11e7-8db2-d413de251a16"), "T07403", "101", "ATMB2_H1", 2016, 3, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("972e74bb-6fb3-4003-a38a-9b12bc73c1f7"), "종합설계", Some("Capstone Design"), 3, 3),
    Course(UUID.fromString("3474f572-46ed-11e7-8db2-d413de251a16"), "C04533", "101", "ACDA1_H1", 2016, 3, UUID.fromString("14fda29b-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("45cb7741-f06f-410d-9c0b-60ea42944580"), "미디어심리학", Some("Media Psychology"), 3, 3),
    Course(UUID.fromString("3f3d6a40-46ed-11e7-8db2-d413de251a16"), "C04318", "101", "ACDA1_H1", 2016, 3, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f1843f6b-f6aa-4540-8941-b462239abaa4"), "언론정보윤리와법", Some("Journalism Ethics and Law"), 3, 3),
    Course(UUID.fromString("3f87052c-46ed-11e7-8db2-d413de251a16"), "C04526", "101", "ACDA1_H1", 2016, 3, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("4908db2c-bdca-4aae-ba0e-07eb4d9a6de8"), "미디어와경제", Some("Media and Economy"), 3, 3),
    Course(UUID.fromString("05924cfa-9da6-43f1-8b1c-61ae0899eb09"), "A12345", "101", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("e47e79fe-235d-48b6-8d5a-4163c89bb59f"), "교양영어", None, 3, 3),
    Course(UUID.fromString("180ee7d3-02e8-4769-ab77-51a6f6a99201"), "B45678", "101", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("7eb33ef9-86bd-45d9-b8ac-ed7fd3c46cbf"), "인터넷의이해", None, 3, 3),
    Course(UUID.fromString("aa35becc-7a81-45f6-9d31-3268115f2aac"), "C54321", "101", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("ed577a08-10cb-40bd-bbd9-557b03eb28f7"), "씐나는교양", None, 3, 3),
    Course(UUID.fromString("405ce5f6-46ed-11e7-8db2-d413de251a16"), "D01384", "102", "AEAA1_H1", 2016, 3, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("08983a4e-955b-4f64-8d62-f5ccb9dbc17f"), "International Investment Law", Some("IIL"), 3, 3),
    Course(UUID.fromString("40a021ec-46ed-11e7-8db2-d413de251a16"), "D02369", "102", "AEAA1_H1", 2016, 3, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("d641443e-b975-4c36-ac6f-b86500698311"), "Law & Economics", Some("LNE"), 3, 3),
    Course(UUID.fromString("40e7747d-46ed-11e7-8db2-d413de251a16"), "D01395", "102", "AEAA1_H1", 2016, 3, UUID.fromString("14194feb-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("158a1799-48ee-4612-a5fb-e81811844e1a"), "Econometrics and Data Analysis", Some("ENDA"), 3, 3),
    Course(UUID.fromString("4131d338-46ed-11e7-8db2-d413de251a16"), "T05202", "102", "ATMB2_H1", 2016, 3, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("61c6150f-efb5-4d6e-8654-b23c3655f38a"), "컴퓨터프로그래밍2", Some("컴프2"), 3, 3),
    Course(UUID.fromString("4179e329-46ed-11e7-8db2-d413de251a16"), "V44301", "102", "ATMB2_H1", 2016, 3, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("5bac8e39-992c-4486-ae89-5e4cfbc18f80"), "컴퓨터구조", Some("컴구"), 3, 3),
    Course(UUID.fromString("41c26288-46ed-11e7-8db2-d413de251a16"), "V44303", "102", "ATMB2_H1", 2016, 3, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f061eeed-1d56-487a-a593-0be34fc04200"), "자료구조", Some("자구"), 3, 3),
    Course(UUID.fromString("42111a02-46ed-11e7-8db2-d413de251a16"), "T07403", "102", "ATMB2_H1", 2016, 3, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("fe467d4f-fcc5-4da9-9d90-59011a31cf93"), "종합설계", Some("Capstone Design"), 3, 3),
    Course(UUID.fromString("bd8883e8-a66c-4e30-a4d5-6e9e391f29ae"), "C04533", "102", "ACDA1_H1", 2016, 3, UUID.fromString("14fda29b-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("801d4734-4aa6-45c7-8c63-3e7c518eec0c"), "미디어심리학", Some("Media Psychology"), 3, 3),
    Course(UUID.fromString("e289a0ff-24f5-4160-bb92-b892af05c3b2"), "C04318", "102", "ACDA1_H1", 2016, 3, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("972e74bb-6fb3-4003-a38a-9b12bc73c1f7"), "언론정보윤리와법", Some("Journalism Ethics and Law"), 3, 3),
    Course(UUID.fromString("a82db357-b258-4310-8f04-f75db5c9db04"), "C04526", "102", "ACDA1_H1", 2016, 3, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("45cb7741-f06f-410d-9c0b-60ea42944580"), "미디어와경제", Some("Media and Economy"), 3, 3),
    Course(UUID.fromString("bd157c63-c8de-4247-adf2-874c715c573c"), "A12345", "102", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f1843f6b-f6aa-4540-8941-b462239abaa4"), "교양영어", None, 3, 3),
    Course(UUID.fromString("2a4d0456-ce5e-4cbe-beda-3f0cca72677f"), "B45678", "102", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("4908db2c-bdca-4aae-ba0e-07eb4d9a6de8"), "인터넷의이해", None, 3, 3),
    Course(UUID.fromString("24663699-33f1-423d-a9f5-28f9fc29b754"), "C54321", "102", "GYOYANG", 2016, 3, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("e47e79fe-235d-48b6-8d5a-4163c89bb59f"), "씐나는교양", None, 3, 3),
    Course(UUID.fromString("d87a9a7c-fb24-4e92-ae2f-6051a234e005"), "D01384", "101", "AEAA1_H1", 2017, 1, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("7eb33ef9-86bd-45d9-b8ac-ed7fd3c46cbf"), "International Investment Law", Some("IIL"), 3, 3),
    Course(UUID.fromString("5d66dab2-e083-4234-9e94-57a38297a05e"), "D02369", "101", "AEAA1_H1", 2017, 1, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("ed577a08-10cb-40bd-bbd9-557b03eb28f7"), "Law & Economics", Some("LNE"), 3, 3),
    Course(UUID.fromString("ed1e6d2a-6c45-43ea-9053-a4552027d98c"), "D01395", "101", "AEAA1_H1", 2017, 1, UUID.fromString("14194feb-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("08983a4e-955b-4f64-8d62-f5ccb9dbc17f"), "Econometrics and Data Analysis", Some("ENDA"), 3, 3),
    Course(UUID.fromString("1d98fb32-5e55-485e-a452-cc8b8e18b47d"), "T05202", "101", "ATMB2_H1", 2017, 1, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("d641443e-b975-4c36-ac6f-b86500698311"), "컴퓨터프로그래밍2", Some("컴프2"), 3, 3),
    Course(UUID.fromString("6fbde94e-a301-4970-9820-cc91572dbeac"), "V44301", "101", "ATMB2_H1", 2017, 1, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("158a1799-48ee-4612-a5fb-e81811844e1a"), "컴퓨터구조", Some("컴구"), 3, 3),
    Course(UUID.fromString("56b65eeb-a553-4e1a-84ab-8b1525c3c670"), "V44303", "101", "ATMB2_H1", 2017, 1, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("61c6150f-efb5-4d6e-8654-b23c3655f38a"), "자료구조", Some("자구"), 3, 3),
    Course(UUID.fromString("b0ed2ad1-e589-4eb8-9c78-b14a324bec9b"), "T07403", "101", "ATMB2_H1", 2017, 1, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("5bac8e39-992c-4486-ae89-5e4cfbc18f80"), "종합설계", Some("Capstone Design"), 3, 3),
    Course(UUID.fromString("78a3d43f-27cf-42b8-b8fd-4f4c5edd5ccd"), "C04533", "101", "ACDA1_H1", 2017, 1, UUID.fromString("14fda29b-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f061eeed-1d56-487a-a593-0be34fc04200"), "미디어심리학", Some("Media Psychology"), 3, 3),
    Course(UUID.fromString("a857a857-a19c-4bbd-8f1a-404777258ed2"), "C04318", "101", "ACDA1_H1", 2017, 1, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("fe467d4f-fcc5-4da9-9d90-59011a31cf93"), "언론정보윤리와법", Some("Journalism Ethics and Law"), 3, 3),
    Course(UUID.fromString("33270e8e-bac8-4ba5-aa89-1466f03dc669"), "C04526", "101", "ACDA1_H1", 2017, 1, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("801d4734-4aa6-45c7-8c63-3e7c518eec0c"), "미디어와경제", Some("Media and Economy"), 3, 3),
    Course(UUID.fromString("4c6822cb-12cf-43a6-8b39-f92c5c9f4af7"), "A12345", "101", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("972e74bb-6fb3-4003-a38a-9b12bc73c1f7"), "교양영어", None, 3, 3),
    Course(UUID.fromString("d430ef5c-aa61-4bf5-874e-c4c4b5385403"), "B45678", "101", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("45cb7741-f06f-410d-9c0b-60ea42944580"), "인터넷의이해", None, 3, 3),
    Course(UUID.fromString("2cd158b7-2f2e-4d7d-8203-42f5951cfeda"), "C54321", "101", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f1843f6b-f6aa-4540-8941-b462239abaa4"), "씐나는교양", None, 3, 3),
    Course(UUID.fromString("aee1ad3c-31ff-4ab8-ba59-272209aa06e4"), "D01384", "102", "AEAA1_H1", 2017, 1, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("4908db2c-bdca-4aae-ba0e-07eb4d9a6de8"), "International Investment Law", Some("IIL"), 3, 3),
    Course(UUID.fromString("38ac5d40-e8a2-48bf-862d-3930d13120d1"), "D02369", "102", "AEAA1_H1", 2017, 1, UUID.fromString("13c99d0c-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("e47e79fe-235d-48b6-8d5a-4163c89bb59f"), "Law & Economics", Some("LNE"), 3, 3),
    Course(UUID.fromString("7bf48c29-0ce1-4534-828e-9d563fa5f719"), "D01395", "102", "AEAA1_H1", 2017, 1, UUID.fromString("14194feb-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("7eb33ef9-86bd-45d9-b8ac-ed7fd3c46cbf"), "Econometrics and Data Analysis", Some("ENDA"), 3, 3),
    Course(UUID.fromString("c06af42d-0c5c-43f9-9c40-c5513707603b"), "T05202", "102", "ATMB2_H1", 2017, 1, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("ed577a08-10cb-40bd-bbd9-557b03eb28f7"), "컴퓨터프로그래밍2", Some("컴프2"), 3, 3),
    Course(UUID.fromString("7b360f15-f2e1-4f88-aae8-367105e85b14"), "V44301", "102", "ATMB2_H1", 2017, 1, UUID.fromString("1464f8d6-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("08983a4e-955b-4f64-8d62-f5ccb9dbc17f"), "컴퓨터구조", Some("컴구"), 3, 3),
    Course(UUID.fromString("a2716ee6-3318-4095-9a38-c44636d0ba8f"), "V44303", "102", "ATMB2_H1", 2017, 1, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("d641443e-b975-4c36-ac6f-b86500698311"), "자료구조", Some("자구"), 3, 3),
    Course(UUID.fromString("b53d9e5f-20cf-42dc-96a1-33b95c0ea3c0"), "T07403", "102", "ATMB2_H1", 2017, 1, UUID.fromString("14b207ef-46e2-11e7-8db2-d413de251a16"), UUID.fromString("163c0f0f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("158a1799-48ee-4612-a5fb-e81811844e1a"), "종합설계", Some("Capstone Design"), 3, 3),
    Course(UUID.fromString("79556e70-4aee-43be-87a1-27b635cead1f"), "C04533", "102", "ACDA1_H1", 2017, 1, UUID.fromString("14fda29b-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("61c6150f-efb5-4d6e-8654-b23c3655f38a"), "미디어심리학", Some("Media Psychology"), 3, 3),
    Course(UUID.fromString("42045612-14bd-4b67-a269-38f4926f16db"), "C04318", "102", "ACDA1_H1", 2017, 1, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("5bac8e39-992c-4486-ae89-5e4cfbc18f80"), "언론정보윤리와법", Some("Journalism Ethics and Law"), 3, 3),
    Course(UUID.fromString("661deb64-6233-443b-b817-6a9d10ed39a8"), "C04526", "102", "ACDA1_H1", 2017, 1, UUID.fromString("154f242f-46e2-11e7-8db2-d413de251a16"), UUID.fromString("15ec0fc8-46e2-11e7-8db2-d413de251a16"), UUID.fromString("f061eeed-1d56-487a-a593-0be34fc04200"), "미디어와경제", Some("Media and Economy"), 3, 3),
    Course(UUID.fromString("3158389a-00a0-4c4a-9cdd-5ad39b529f9a"), "A12345", "102", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("fe467d4f-fcc5-4da9-9d90-59011a31cf93"), "교양영어", None, 3, 3),
    Course(UUID.fromString("7aa7f23a-53dc-44e4-8417-35f6367e155b"), "B45678", "102", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("801d4734-4aa6-45c7-8c63-3e7c518eec0c"), "인터넷의이해", None, 3, 3),
    Course(UUID.fromString("53a2f3cd-868b-4938-84c4-892febccee41"), "C54321", "102", "GYOYANG", 2017, 1, UUID.fromString("159e7537-46e2-11e7-8db2-d413de251a16"), UUID.fromString("168eb65a-46e2-11e7-8db2-d413de251a16"), UUID.fromString("972e74bb-6fb3-4003-a38a-9b12bc73c1f7"), "씐나는교양", None, 3, 3)
  )
}
