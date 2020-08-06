lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-lab",
    description := "Laboratory for playing with Dotty (Scala 3)",
    version := "0.1.0",
    
    libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api"  % "2.13.3",
      "org.apache.logging.log4j" % "log4j-core" % "2.13.3"
    ),

    scalaVersion := "0.26.0-RC1"

  )

