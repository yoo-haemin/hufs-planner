package models

import java.util.UUID

case class MajorSubject(majorID: UUID, subject: Subject, required: Boolean)
