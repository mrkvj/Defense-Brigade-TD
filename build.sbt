ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "DefenceBrigadeTD"
  )

libraryDependencies += "org.scalafx" % "scalafx_3" % "20.0.0-R31"
libraryDependencies += "com.lihaoyi" %% "upickle" % "3.1.4"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.9.3"

libraryDependencies ++= Seq(
  "org.openjfx" % "javafx-controls" % "21.0.2", // Adjust version as needed
  "org.openjfx" % "javafx-fxml" % "21.0.2"
)
/*
fork := true
javaOptions ++= Seq(
  "--module-path", "path/to/javafx/lib", // Path to JavaFX SDK
  "--add-modules", "javafx.controls,javafx.fxml"
)
 */

