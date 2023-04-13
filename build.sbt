ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

ThisBuild / name := "nino-validation-core"

lazy val `attribute-validation-core` = (project in file("."))
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(
    scalaVersion := "2.13.10",
    majorVersion := 0,
    libraryDependencies ++= Seq(
      "com.googlecode.libphonenumber" % "libphonenumber" % "8.13.9",
      "com.googlecode.libphonenumber" % "geocoder" % "2.201",
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalactic" %% "scalactic" % "3.2.15" % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.11.0" % Test,
      "com.vladsch.flexmark" % "flexmark-all" % "0.64.0" % Test
    ),
    Test / fork := true
  )
