package shared

import scala.concurrent.Future
import java.time.Year
import models.Semester._

object Util {
  val nextSemester = Year.of(2017) -> FirstSemester

  def reverseEffect[A](as: Seq[Future[A]]): Future[Seq[A]] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    (Future.successful(Seq[A]()) /: as) { (acc, a) =>
      (acc zip a) map { case (as, a) => as :+ a }
    }
  }
}
