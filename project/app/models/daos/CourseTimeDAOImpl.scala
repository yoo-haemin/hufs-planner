package models.daos

import java.util.UUID
import java.time.{ Year, DayOfWeek }
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.Future
import scala.collection.immutable.SortedSet
import models.CourseTime

class CourseTimeDAOImpl @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider) extends CourseTimeDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._
  val courseTimes = TableQuery[CourseTimesTable]

  def findById(courseId: UUID): Future[Option[CourseTime]] =
    db.run(courseTimes.filter(_.courseId === courseId).result.headOption)

  def all(): Future[Seq[CourseTime]] =
    db.run(courseTimes.result)

  def insert(courseTime: CourseTime): Future[UUID] =
    db.run(courseTimes returning courseTimes.map(_.courseId) += courseTime)

  class CourseTimesTable(tag: Tag) extends Table[CourseTime](tag, "course_times") {
    def courseId = column[UUID]("course_id")
    def timeMon = column[Option[Int]]("time_mon")
    def timeTue = column[Option[Int]]("time_tue")
    def timeWed = column[Option[Int]]("time_wed")
    def timeThu = column[Option[Int]]("time_thu")
    def timeFri = column[Option[Int]]("time_fri")
    def timeSat = column[Option[Int]]("time_sat")
    def timeSun = column[Option[Int]]("time_sun")
    def roomMon = column[Option[String]]("room_mon")
    def roomTue = column[Option[String]]("room_tue")
    def roomWed = column[Option[String]]("room_wed")
    def roomThu = column[Option[String]]("room_thu")
    def roomFri = column[Option[String]]("room_fri")
    def roomSat = column[Option[String]]("room_sat")
    def roomSun = column[Option[String]]("room_sun")

    def rowToSeq(
      t: Tuple15[java.util.UUID, Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String]]
    ): CourseTime = {
      @annotation.tailrec
      def extractTime(timeColumn: Int, cur: Int, acc: SortedSet[Int]): SortedSet[Int] =
        if (cur == 33) acc
        else if (timeColumn % 2 == 1) extractTime(timeColumn >>> 1, cur + 1, acc + cur)
        else extractTime(timeColumn >>> 1, cur + 1, acc)

      CourseTime(
        t._1,
        (Seq(
        (DayOfWeek.MONDAY, t._2, t._9),
        (DayOfWeek.TUESDAY, t._3, t._10),
        (DayOfWeek.WEDNESDAY, t._4, t._11),
        (DayOfWeek.THURSDAY, t._5, t._12),
        (DayOfWeek.FRIDAY, t._6, t._13),
        (DayOfWeek.SATURDAY, t._7, t._14),
        (DayOfWeek.SUNDAY, t._8, t._15)
      ) filter {
          case (dayOfWeek: DayOfWeek, Some(i), Some(s)) => true
          case _ => false
        } map { t =>
          (t._1 -> (extractTime(t._2.get, 0, SortedSet[Int]()) -> t._3.get))
        }).toMap)
    }

    def courseTimeToRow(courseTime: CourseTime): Option[Tuple15[UUID, Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[Int], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String]]] = courseTime match {
      case CourseTime(id, xs) if xs.size > 0 => { // The course has time
        //Convert individual timesets to int
        def timeSeqToInt(xs: SortedSet[Int]): Int =
          xs.map(i => Math.pow(2, i.toDouble).toInt).sum

        //Convert the whole Map into seq for individual dayofweek
        def toRow(time: Map[DayOfWeek, (SortedSet[Int], String)]): Seq[(Option[Int], Option[String])] = {
          val timeMap = time.foldLeft(Map[DayOfWeek, (Int, String)]()) {
            (acc, t) =>
              {
                t._2 match {
                  case (time, room) =>
                    acc + (t._1 -> (timeSeqToInt(time) -> room))
                }
              }
          }
          Seq(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
            .map(timeMap.get(_) match {
              case Some((i, s)) => Some(i) -> Some(s)
              case None => None -> None
            })
        }

        //Create return value from above helper methods
        val timeTuple = (
          id,
          toRow(xs)(0)._1, toRow(xs)(1)._1, toRow(xs)(2)._1, toRow(xs)(3)._1, toRow(xs)(4)._1, toRow(xs)(5)._1, toRow(xs)(6)._1,
          toRow(xs)(0)._2, toRow(xs)(1)._2, toRow(xs)(2)._2, toRow(xs)(3)._2, toRow(xs)(4)._2, toRow(xs)(5)._2, toRow(xs)(6)._2
        )

        Some(timeTuple)
      }
      case _ => None
    }

    def * = (courseId, timeMon, timeTue, timeWed, timeThu, timeFri, timeSat, timeSun,
      roomMon, roomTue, roomWed, roomThu, roomFri, roomSat, roomSun) <> (
        rowToSeq _, courseTimeToRow _)
  }

  implicit val yearColumnType: BaseColumnType[Year] =
    MappedColumnType.base[Year, Int](_.getValue, Year.of _)
}
