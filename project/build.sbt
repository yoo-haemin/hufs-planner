import com.typesafe.sbt.SbtScalariform._

import scalariform.formatter.preferences._

name := """hufs-planner"""

version := "0.0.1"

scalaVersion := "2.11.11"

resolvers += Resolver.jcenterRepo
resolvers += "Lambda Fun Repo" at "https://repo.lambda.fun/artifactory/sbt-dev-local/"


libraryDependencies ++= Seq(
  //Dependencies for Silhouette
  "com.mohiva" %% "play-silhouette" % "4.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "4.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "4.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "4.0.0",
  "com.mohiva" %% "play-silhouette-testkit" % "4.0.0" % "test",

  //Dependencies for WebJars
  "org.webjars" %% "webjars-play" % "2.5.0-2",
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3",
  "org.webjars" % "ionicons" % "2.0.1",
  "org.webjars" % "zxcvbn" % "4.3.0",

  //Dependencies for Slick
  "com.typesafe.play" %% "play-slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.1.0",
  "mysql" % "mysql-connector-java" % "5.1.23",
  jdbc,
  //"com.h2database" % "h2" % "1.4.195",

  //General Dependencies
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.iheart" %% "ficus" % "1.2.6",
  "com.typesafe.play" %% "play-mailer" % "5.0.0",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.5.0-akka-2.4.x",
  specs2 % Test,
  cache,
  filters
  //"fun.lambda" %% "hufs-course-crawler" % "0.0.1"
  )


lazy val root = (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator

routesImport += "utils.route.Binders._"

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen" // Warn when numerics are widened.
)

//********************************************************
// Scalariform settings
//********************************************************

defaultScalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(FormatXml, false)
  .setPreference(DoubleIndentClassDeclaration, false)
  .setPreference(DanglingCloseParenthesis, Preserve)
