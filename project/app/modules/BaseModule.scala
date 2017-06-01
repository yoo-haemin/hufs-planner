package modules

import com.google.inject.AbstractModule
import models.daos._
import models.services._
import net.codingwell.scalaguice.ScalaModule

/**
 * The base Guice module.
 */
class BaseModule extends AbstractModule with ScalaModule {

  /**
   * Configures the module.
   */
  def configure(): Unit = {
    bind[AuthTokenDAO].to[hashmap.AuthTokenDAOImpl]
    bind[CourseDAO].to[hashmap.CourseDAOImpl]
    bind[CourseTimeDAO].to[hashmap.CourseTimeDAOImpl]
    bind[DepartmentDAO].to[hashmap.DepartmentDAOImpl]
    bind[DepartmentTimeDAO].to[hashmap.DepartmentTimeDAOImpl]
    bind[MajorDAO].to[hashmap.MajorDAOImpl]
    bind[SubjectDAO].to[hashmap.SubjectDAOImpl]
    bind[UserDAO].to[hashmap.UserDAOImpl]
    bind[UserCourseDAO].to[hashmap.UserCourseDAOImpl]

    bind[AuthTokenService].to[AuthTokenServiceImpl]
    bind[MajorService].to[MajorServiceImpl]
    bind[UserCourseService].to[UserCourseServiceImpl]
  }
}
