package models.daos.hashmap

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future
import models.CourseTime
import models.daos.CourseTimeDAO
import java.time.DayOfWeek._
import collection.immutable.SortedSet

class CourseTimeDAOImpl @Inject() extends CourseTimeDAO {
  def findById(id: UUID): Future[Option[CourseTime]] =
    Future.successful(CourseTimeDAOImpl.courseTimes.filter(_.id == id).headOption)

  def all(): Future[Seq[CourseTime]] =
    Future.successful(CourseTimeDAOImpl.courseTimes.toSeq)

  def insert(courseTime: CourseTime): Future[UUID] =
    Future.successful {
      CourseTimeDAOImpl.courseTimes = CourseTimeDAOImpl.courseTimes + courseTime
      courseTime.id
    }
}

object CourseTimeDAOImpl {
  var courseTimes = Set[CourseTime](
    CourseTime(
      id = UUID.fromString("158a1799-48ee-4612-a5fb-e81811844e1a"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "3201"))),

    CourseTime(
      id = UUID.fromString("61c6150f-efb5-4d6e-8654-b23c3655f38a"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "2201"))),

    CourseTime(
      id = UUID.fromString("5bac8e39-992c-4486-ae89-5e4cfbc18f80"),
      value = Map(MONDAY -> (SortedSet(4, 5, 6) -> "3201"))),

    CourseTime(
      id = UUID.fromString("f061eeed-1d56-487a-a593-0be34fc04200"),
      value = Map(TUESDAY -> (SortedSet(1, 2, 3) -> "3201"))),

    CourseTime(
      id = UUID.fromString("fe467d4f-fcc5-4da9-9d90-59011a31cf93"),
      value = Map(TUESDAY -> (SortedSet(1, 2, 3) -> "2201"))),

    CourseTime(
      id = UUID.fromString("801d4734-4aa6-45c7-8c63-3e7c518eec0c"),
      value = Map(TUESDAY -> (SortedSet(4, 5, 6) -> "3201"))),

    CourseTime(
      id = UUID.fromString("972e74bb-6fb3-4003-a38a-9b12bc73c1f7"),
      value = Map(WEDNESDAY -> (SortedSet(1, 2, 3) -> "3201"))),

    CourseTime(
      id = UUID.fromString("45cb7741-f06f-410d-9c0b-60ea42944580"),
      value = Map(WEDNESDAY -> (SortedSet(1, 2, 3) -> "2201"))),

    CourseTime(
      id = UUID.fromString("f1843f6b-f6aa-4540-8941-b462239abaa4"),
      value = Map(WEDNESDAY -> (SortedSet(4, 5, 6) -> "3201"))),

    CourseTime(
      id = UUID.fromString("4908db2c-bdca-4aae-ba0e-07eb4d9a6de8"),
      value = Map(THURSDAY -> (SortedSet(1, 2, 3) -> "3201"))),

    CourseTime(
      id = UUID.fromString("e47e79fe-235d-48b6-8d5a-4163c89bb59f"),
      value = Map(THURSDAY -> (SortedSet(1, 2, 3) -> "2201"))),

    CourseTime(
      id = UUID.fromString("7eb33ef9-86bd-45d9-b8ac-ed7fd3c46cbf"),
      value = Map(THURSDAY -> (SortedSet(4, 5, 6) -> "3201"))),

    CourseTime(
      id = UUID.fromString("ed577a08-10cb-40bd-bbd9-557b03eb28f7"),
      value = Map(FRIDAY -> (SortedSet(1, 2, 3) -> "3201"))),

    CourseTime(
      id = UUID.fromString("08983a4e-955b-4f64-8d62-f5ccb9dbc17f"),
      value = Map(FRIDAY -> (SortedSet(1, 2, 3) -> "2201"))),

    CourseTime(
      id = UUID.fromString("d641443e-b975-4c36-ac6f-b86500698311"),
      value = Map(FRIDAY -> (SortedSet(4, 5, 6) -> "3201")))
  )
}

/*
    CourseTime(
      id = UUID.fromString("452a2bcc-958e-4fde-8a95-c69fbb196d45"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("56183404-f589-4e89-9245-5f843b90de90"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "2201"))
        ),
    CourseTime(
      id = UUID.fromString("7e64b3af-5b2a-46bc-a265-4043f0dd7ad0"),
      value = Map(MONDAY -> (SortedSet(4, 5, 6) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("41ac710e-e0ec-44a6-b06b-a39f720e83e0"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("e4d840de-e22b-4ce6-82f4-26cd599041b5"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "2201"))
        ),
    CourseTime(
      id = UUID.fromString("3ba53623-31b0-4309-9f21-d1c9e1d9dd69"),
      value = Map(MONDAY -> (SortedSet(4, 5, 6) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("e093a254-828b-40bc-b0fb-3d564966aa48"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("65d425fa-0019-4788-bfc1-fc35a643107a"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "2201"))
        ),
    CourseTime(
      id = UUID.fromString("57f8808e-a913-4058-8043-28147002e656"),
      value = Map(MONDAY -> (SortedSet(4, 5, 6) -> "3201"))
        ),
    CourseTime(
      id = UUID.fromString("346f8a21-d70e-46fe-bc6b-95d463350f3c"),
      value = Map(MONDAY -> (SortedSet(1, 2, 3) -> "3201"))
        )


 */
