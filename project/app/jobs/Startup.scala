package jobs

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers._
import models.{ User, UserMajor }
import models.MajorType._
import models.services.{ AuthTokenService, UserService, MajorService, UserMajorService }
import utils.auth.DefaultEnv
import shared.Util.reverseEffect

import play.api.libs.concurrent.Execution.Implicits._

/**
 * Startup: Creates sample data for demonstration purposes
 */
@Singleton
class Startup @Inject() (
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  majorService: MajorService,
  userMajorService: UserMajorService,
  authInfoRepository: AuthInfoRepository,
  authTokenService: AuthTokenService,
  passwordHasherRegistry: PasswordHasherRegistry) {

  //Create Sample user
  //Major: 국통2013, 융소2013
  //
  val loginInfo = LoginInfo(CredentialsProvider.ID, "example@jamioni.com")
  val authInfo = passwordHasherRegistry.current.hash("asdf")
  for {
    fmOption <- majorService.find("국제통상학과", FirstMajor, 2013)
    smOption <- majorService.find("융복합소프트웨어", SecondMajor, 2013)
    userData = User(
      userID = UUID.fromString("42944783-ee3f-4a92-85b9-777c94d9dc5e"),
      loginInfo = loginInfo,
      email = Some("example@jamioni.com"),
      classYear = 2013,
      semester = 5,
      activated = true
    )
    userMajors = Seq(fmOption, smOption).filterNot(_.isEmpty).map(_.get).map(m => UserMajor(userData.userID, m.id))
    savedUserMajors <- reverseEffect(userMajors.map(userMajor => userMajorService.save(userMajor)))
    user <- userService.save(userData)
    authInfo <- authInfoRepository.add(loginInfo, authInfo)
    authToken <- authTokenService.create(user.userID)
  } yield ()
}
