package shared

import scala.concurrent.Future

object Util {
  def reverseEffect[A](as: Seq[Future[A]]): Future[Seq[A]] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    (Future.successful(Seq[A]()) /: as) { (acc, a) =>
      (acc zip a) map { case (as, a) => as :+ a }
    }
  }
}
