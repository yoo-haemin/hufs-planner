name := "hufs_planner"

version := "1.0"

lazy val `hufs_planner` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc, cache, ws, specs2 % Test ,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.squeryl" % "squeryl_2.11" % "0.9.8"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value
//resolvers += "pentaho" at "http://repository.pentaho.org/artifactory/repo/"
/*
("org.reactivemongo" % "reactivemongo" % reactiveMongoVersion)
    .exclude("com.typesafe.akka", "akka-actor_2.10")
    .exclude("org.scalaz", "scalaz-effect")
    .exclude("org.scalaz", "scalaz-core")*/
