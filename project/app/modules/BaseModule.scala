package modules

import com.google.inject.AbstractModule
import models.daos._
import models.services.{ AuthTokenService, AuthTokenServiceImpl }
import net.codingwell.scalaguice.ScalaModule

/**
 * The base Guice module.
 */
class BaseModule extends AbstractModule with ScalaModule {

  /**
   * Configures the module.
   */
  def configure(): Unit = {
    bind[AuthTokenDAO].to[AuthTokenDAOImpl]
    bind[AuthTokenService].to[AuthTokenServiceImpl]
    //bind[CourseDAO].to[CourseDAO]
    bind[CourseTimeDAO].to[CourseTimeDAOImpl]
    bind[DepartmentDAO].to[DepartmentDAOImpl]
    bind[DepartmentTimeDAO].to[DepartmentTimeDAOImpl]
    bind[MajorDAO].to[MajorDAOImpl]
    bind[MajorTypeDAO].to[MajorTypeDAOImpl]
    bind[SubjectDAO].to[SubjectDAOImpl]
    bind[UserDAO].to[UserDAOImpl]
  }
}
