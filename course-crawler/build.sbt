import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "kr.pe.zzz",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "HUFS Course Crawler",
    libraryDependencies ++=
      Seq(
        scalaTest % Test,
        "org.scalaj" %% "scalaj-http" % "2.3.0",
        "org.json4s" %% "json4s-jackson" % "3.5.1"
      )
  )
