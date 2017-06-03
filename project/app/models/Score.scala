package models

sealed abstract class Score(v: Int) {
  import Score._
  override def toString: String = this match {
    case Ap => "A+"
    case Az => "A0"
    case Bp => "B+"
    case Bz => "B0"
    case Cp => "C+"
    case Cz => "C0"
    case Dp => "D+"
    case Dz => "D0"
    case Fz => "F"
    case Pass => "Pass"
    case Fail => "Fail"
  }

  def retakable: Boolean = this match {
    case Cp => true
    case Cz => true
    case Dp => true
    case Dz => true
    case Fz => true
    case Fail => true
    case _ => false

  }

  def failed: Boolean = this match {
    case Fz => true
    case Fail => true
    case _ => false
  }

  def passed: Boolean = !this.failed
}

object Score {
  import scala.language.implicitConversions

  final case object Ap extends Score(9)
  final case object Az extends Score(8)
  final case object Bp extends Score(7)
  final case object Bz extends Score(6)
  final case object Cp extends Score(5)
  final case object Cz extends Score(4)
  final case object Dp extends Score(3)
  final case object Dz extends Score(2)
  final case object Fz extends Score(0)
  final case object Pass extends Score(-1)
  final case object Fail extends Score(-2)

  implicit def toInt(s: Score): Int = s match {
    case Ap => 9
    case Az => 8
    case Bp => 7
    case Bz => 6
    case Cp => 5
    case Cz => 4
    case Dp => 3
    case Dz => 2
    case Fz => 0
    case Pass => -1
    case Fail => -2
  }

  def toGrade(s: Score): Double = s match {
    case Ap => 4.5
    case Az => 4.0
    case Bp => 3.5
    case Bz => 3.0
    case Cp => 2.5
    case Cz => 2.0
    case Dp => 1.5
    case Dz => 1.0
    case Fz => 0.0
    case Pass => 0.0
    case Fail => 0.0
  }

  implicit def fromInt(i: Int): Score = i match {
    case 9 => Ap
    case 8 => Az
    case 7 => Bp
    case 6 => Bz
    case 5 => Cp
    case 4 => Cz
    case 3 => Dp
    case 2 => Dz
    case 0 => Fz
    case -1 => Pass
    case -2 => Fail
  }

  def avg(ss: Seq[(Score, Int)]): Double = {
    val woPF = ss.filterNot { case (s, i) => s == Pass || s == Fail }
    val (scoreSum, creditSum) = ((0.0 -> 0) /: woPF) {
      case ((scoreAcc, creditAcc), (score, credit)) =>
        (scoreAcc + toGrade(score) * credit) -> (creditAcc + credit)
    }
    scoreSum / creditSum
  }
}
