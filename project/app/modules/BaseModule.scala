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
    bind[MajorSubjectDAO].to[hashmap.MajorSubjectDAOImpl]

    bind[AuthTokenService].to[AuthTokenServiceImpl]
    //bind[CourseService].to[CourseServiceImpl]
    //bind[CourseTimeService].to[CourseTimeServiceImpl]
    //bind[DepartmentService].to[DepartmentServiceImpl]
    //bind[DepartmentTimeService].to[DepartmentTimeServiceImpl]
    bind[MajorService].to[MajorServiceImpl]
    //bind[SubjectService].to[SubjectServiceImpl]
    bind[UserService].to[UserServiceImpl]
    bind[UserCourseService].to[UserCourseServiceImpl]
    //bind[MajorSubjectService].to[MajorSubjectServiceImpl]
  }
}
