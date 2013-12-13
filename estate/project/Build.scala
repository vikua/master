import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "estate"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        jdbc,
        anorm,

        "com.typesafe.play" %% "play-slick" % "0.5.0.8",
//        "com.typesafe.slick" % "slick_2.10" % "1.0.1",
//        "com.google.inject" % "guice" % "3.0",
//        "javax.inject" % "javax.inject" % "1",

        "org.scalatest" %% "scalatest" % "2.0" % "test" withSources(),
        "org.mockito" % "mockito-core" % "1.9.5" % "test" withSources()
    )


    val main = play.Project(appName, appVersion, appDependencies).settings(
        scalaVersion := "2.10.3"
    )

}
