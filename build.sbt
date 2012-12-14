name := "Frequency"

organization := "com.edgesysdesign"

version := "master"

scalaVersion := "2.10.0-RC3"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "org.scalatest" % "scalatest_2.10.0-RC3" % "1.8-B1" % "test"

publishTo := Some(
  Resolver.sftp(
    "ESD Repository",
    "jvmrepo.edgesysdesign.com",
    22,
    "jvmrepo/") as("buildsys", new File("/home/ricky/buildbot/buildsys_id_rsa")))
