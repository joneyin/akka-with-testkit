name := "mapp-test"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val scalaLibVersion = scalaVersion.value
  val akkaLibVersion = "2.3.12"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaLibVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaLibVersion % Test,
    "org.scalatest" %% "scalatest" % "2.2.1" % Test,
    "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % Test
  )
}