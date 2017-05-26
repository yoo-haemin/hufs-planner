package models
/*
import java.util.UUID
import javax.inject.Inject

import models.daos.UserDAOImpl
import scala.concurrent.Await

import scala.concurrent.duration._
import play.api.db.Databases

import play.api.inject.guice.GuiceApplicationBuilder

import com.google.inject.AbstractModule
import com.mohiva.play.silhouette.api.{ Environment, LoginInfo }
import com.mohiva.play.silhouette.test._
import net.codingwell.scalaguice.ScalaModule
import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.concurrent.Execution.Implicits._
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }
import utils.auth.DefaultEnv

/**
 * Test case for the [[models.daos.UserDAOImpl]] class.
 */
class UserDAOImplSpec @Inject() (users: UserDAOImpl) extends PlaySpecification with Mockito {
  import models.User

  val application = new GuiceApplicationBuilder()
    .overrides(bind[DatabaseConfigProvider].to[MockComponent])
    .build

  val testUser = User(
    userID = UUID.randomUUID(),
    loginInfo = LoginInfo("", ""),
    email = Some("a@a.com"),
    classYear = 0,
    activated = false)

  val database = Databases.withInMemory(
    name = "mydatabase",
    urlOptions = Map("MODE" -> "MYSQL"),
    config = Map("logStatements" -> true)) { database =>
      sequential

      "The `User` DAO" should {
        "Be able to save a new User" in {
          Await.result(users.save(testUser), 10000 nanos) shouldEqual testUser
        }
      }
    }

}
 */
