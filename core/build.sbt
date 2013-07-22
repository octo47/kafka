import sbt._
import Keys._
import AssemblyKeys._

name := "kafka"

resolvers ++= Seq(
  "SonaType ScalaTest repo" at "https://oss.sonatype.org/content/groups/public/org/scalatest/"
)

libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )

libraryDependencies ++= Seq(
  "org.apache.zookeeper"  % "zookeeper"   % "3.3.4",
  "com.101tec"            % "zkclient"     % "0.3",
  "org.xerial.snappy"     % "snappy-java" % "1.0.4.1",
  "com.yammer.metrics"    % "metrics-core" % "2.2.0",
  "com.yammer.metrics"    % "metrics-annotation" % "2.2.0",
  "org.easymock"          % "easymock"    % "3.0" % "test",
  "junit"                 % "junit"       % "4.1" % "test"
)

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
  deps :+ (sv match {
    case "2.8.2" => "org.scalatest" %% "scalatest" % "1.8" % "test"
    case _       => "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  })
}

assemblySettings

unmanagedSourceDirectories in Compile <+= (sourceDirectory in Compile, scalaVersion){ (s,v) => 
  s / ("scala-" + (v match {
    case v if v.startsWith("2.8.") => "2.8.x" 
    case _ => "2.9.x" // also for 2.10
  })) 
}

