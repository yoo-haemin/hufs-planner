package models

/**
 * A subject is a set of courses recognized by a specific major.
 * A subject may be required for graduation, or it simply may be recognized.
 * Courses are concrete "instances" of subjects.
 */
case class Subject(
  code: String,
  name1: String,
  name2: Option[String],
  department: Department
) {
  require(code.length <= Subject.length, s"Code length must be less than ${Subject.length}")

  def equals(that: Subject): Boolean =
    if (this.code == that.code && this.department == that.department) true
    else false
}

object Subject {
  val length = 6
}
