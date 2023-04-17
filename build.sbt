import sbt.Keys._
import sbt._

ThisBuild / name := "attribute-validation-core"

ThisBuild / organization := "uk.gov.hmrc"

val scala2_12 = "2.12.17"
val scala2_13 = "2.13.10"

lazy val `attribute-validation-core` = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    scalaVersion := scala2_13,
    crossScalaVersions := Seq(scala2_12, scala2_13),
    majorVersion := 0,
    isPublicArtefact := true,
    publish / skip := true,
    libraryDependencies ++= Seq(
      "com.googlecode.libphonenumber" % "libphonenumber" % "8.13.9",
      "com.googlecode.libphonenumber" % "geocoder" % "2.201",
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "com.vladsch.flexmark" % "flexmark-all" % "0.64.0" % Test
    ),
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := "uk.gov.hmrc.attribute_validation",
    Test / fork := true
  )
