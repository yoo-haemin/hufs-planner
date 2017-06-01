package jobs

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers._
import models.User
import models.MajorType._
import models.services.{ AuthTokenService, UserService, MajorService }
import utils.auth.DefaultEnv

import play.api.libs.concurrent.Execution.Implicits._

/**
 * Startup: Creates sample user for demonstration purposes
 */
@Singleton
class Startup @Inject() (
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  majorService: MajorService,
  authInfoRepository: AuthInfoRepository,
  authTokenService: AuthTokenService,
  passwordHasherRegistry: PasswordHasherRegistry) {
  val loginInfo = LoginInfo(CredentialsProvider.ID, "example@jamioni.com")
  val authInfo = passwordHasherRegistry.current.hash("asdf")
  for {
    fmOption <- majorService.find("국제통상학과", FirstMajor, 2013)
    smOption <- majorService.find("융복합소프트웨어", SecondMajor, 2013)
    userData = User(
      userID = UUID.randomUUID(),
      loginInfo = loginInfo,
      email = Some("example@jamioni.com"),
      classYear = 2013,
      semester = 5,
      major = Seq(fmOption, smOption)
        .filterNot(_.isEmpty)
        .map(_.get)
        .map(m => m.majorType -> m)
        .toMap,
      activated = true
    )
    user <- userService.save(userData.copy())
    authInfo <- authInfoRepository.add(loginInfo, authInfo)
    authToken <- authTokenService.create(user.userID)
  } yield ()
}
