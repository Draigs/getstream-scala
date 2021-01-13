name := "getstream-scala-client"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
	"org.json4s" %% "json4s-jackson" % "3.6.10",
	"org.json4s" %% "json4s-ext" % "3.6.10",
	"com.twitter" %% "finagle-http" % "20.12.0",
	"com.typesafe" % "config" % "1.4.1",
	"joda-time" % "joda-time" % "2.10.9",
	"org.apache.httpcomponents" % "httpcore" % "4.4",
	"org.apache.httpcomponents" % "httpclient" % "4.4",
	"org.scalatest" %% "scalatest" % "3.2.2" % Test
)
