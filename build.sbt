
name := "redis-plugin"
organization := "eu.monniot.redis"

sbtPlugin := true

scalaVersion in Global := "2.10.6"

version := "0.1"

resolvers += Resolver.jcenterRepo

libraryDependencies += "eu.monniot.redis" % "embedded-redis" % "1.0.3"

// Scripted - sbt plugin tests
scriptedSettings
scriptedLaunchOpts <+= version apply { v => "-Dproject.version=" + v }