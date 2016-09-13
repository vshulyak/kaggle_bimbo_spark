import sbtassembly.AssemblyPlugin.autoImport._
import sbtsparksubmit.SparkSubmitPlugin.autoImport._

name := "Bimbo"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.0.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.0.0" % "provided"
)

dependencyOverrides ++= Set(
  "org.scala-lang" % "scala-compiler" % scalaVersion.value
)

// Use assembly to create the uber jar
sparkSubmitJar := assembly.value.getAbsolutePath

// instead of just `SparkSubmit.settings` we have to use this:
lazy val root = (project in file("."))
  .settings(SparkSubmit.settings: _*)
