name := "hufs_planner"

version := "1.0"

lazy val `hufs_planner` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc , cache , ws   , specs2 % Test ,
  "postgresql" % "postgresql" % "9.4.1208-jdbc42-atlassian-hosted",
  "org.squeryl" %% "squeryl" % "0.9.8"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "pentaho" at "http://repository.pentaho.org/artifactory/repo/"

