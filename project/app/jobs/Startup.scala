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
 * Startup: Creates sample data for demonstration purposes
 */
@Singleton
class Startup @Inject() (
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  majorService: MajorService,
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

  //Create Subjects(Tied to major):
  /*
   Each 2 per semester(total 4): 융복합, 국통, 언정

   융복합:
   종합설계
   운영체제

   자료구조
   데이터베이스

   국통:
   International Investment Law
   Law & Economics

   Econometrics and Data Analysis


   언정:
   미디어와 경제
   저널리즘의 이해

   커뮤니케이션연구방법론
   미디어심리학

   */

  //Create Major: 국통2013, 언정2012, 융소2013, 융소2012
  //TODO: Create required subjects

  //Create Departments:
  /*
   국통2016-3, 국통2017-1
   언정2016-3, 언정2017-1
   융소2016-3, 융소2017-1
   교양2016-3, 교양2017-1
   */

  //Create Subjects: 융복합 4 * 2(년도별)
  /*

   */

  //Create Sample Courses
  /*
   국통2016 국통2017
   언정2016 언정2017
   융소2016 융소2017
   */

  //Create UserCourse for sample user

}
