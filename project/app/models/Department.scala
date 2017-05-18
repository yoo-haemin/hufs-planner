package models

case class Department(
  id: String,
  name: String,
  campus: Campus,
  affiliation: Affiliation
)

sealed abstract class Campus(val v: Byte)

object Campus {
  final case object Seoul extends Campus(0)
  final case object Global extends Campus(1)

  def fromByte(v: Byte): Campus = v match {
    case 0 => Seoul
    case 1 => Global
    case _ => sys.error("Campus must be either Seoul or Global")
  }

  def toByte(campus: Campus): Byte = campus match {
    case Seoul => 0
    case Global => 1
  }
}

sealed abstract class Affiliation(val v: Byte)

object Affiliation {
  final case object Undergraduate extends Affiliation(0)

  def fromByte(v: Byte): Affiliation = v match {
    case 0 => Undergraduate
    case _ => sys.error("Campus must be either Seoul or Global")
  }

  def toByte(aff: Affiliation): Byte = aff match {
    case Undergraduate => 0
  }
}
