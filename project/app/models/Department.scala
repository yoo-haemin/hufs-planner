package models

case class Department(
  id: String,
  name: String,
  campus: Campus,
  affiliation: Affiliation
)

sealed abstract class Campus(val v: Int)

object Campus {
  final case object Seoul extends Campus(0)
  final case object Global extends Campus(1)

  def fromInt(v: Int): Campus = v match {
    case 0 => Seoul
    case 1 => Global
    case _ => sys.error("Campus must be either Seoul or Global")
  }

  def toInt(campus: Campus): Int = campus match {
    case Seoul => 0
    case Global => 1
  }
}

sealed abstract class Affiliation(val v: Int)

object Affiliation {
  final case object Undergraduate extends Affiliation(0)

  def fromInt(v: Int): Affiliation = v match {
    case 0 => Undergraduate
    case _ => sys.error("Campus must be either Seoul or Global")
  }

  def toInt(aff: Affiliation): Int = aff match {
    case Undergraduate => 0
  }
}
