import _root_.redis.embedded.util.{Architecture, OS}

name := "test-project"
version := "1.0"

scalaVersion in Global := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

redisBinaries := Seq(
  ("3.0.7", OS.MAC_OS_X, Architecture.x86_64) -> "path",
  ("3.0.7", OS.UNIX, Architecture.x86_64) -> "path"
)

redisInstances := Seq(
  RedisInstance("3.0.7", "cluster", Seq(7895))
)