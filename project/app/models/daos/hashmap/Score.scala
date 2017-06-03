package models

sealed abstract class Score(v: Int)

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
}
