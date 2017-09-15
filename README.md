# sbt-redis-plugin [![Build Status](https://travis-ci.org/fmonniot/sbt-redis-plugin.svg?branch=master)](https://travis-ci.org/fmonniot/sbt-redis-plugin)

Launch redis before your test and bring it down after. No more waiting for it on each test or test suite!

This is a fork of https://github.com/fmonniot/sbt-redis-plugin that adds:
- SBT 1.x support.
- Out of the box executables for:

| OS type | Architecture | Redis Server Versions |
|---------|--------------|-----------------------|
| Unix    | x86_64       | 3.0.7, 3.2.1          |
| MacOS   | x86          | 3.2.1                 |
| MacOS   | x86_64       | 3.0.7, 3.2.1          |
| Windows | x86_64       | 3.2.1                 |

## Usage

Add this snippet to your `project/plugins.sbt`

```scala
resolvers ++= Seq(
  Resolver.bintrayRepo("tarossi", "maven-public"),
  Resolver.bintrayIvyRepo("tarossi", "ivy-public")
)

addSbtPlugin("eu.monniot.redis" % "redis-plugin" % "0.6.0")
```

You can now configure the plugin:

```scala
// [Optional] Configure a redis binary matrix
redisBinaries := Seq(
  ("4.0.1", OS.UNIX, Architecture.x86_64) -> "/path/to/the/correct/redis-server-4.0.1"
)

// Launch a server and a cluster
redisInstances := Seq(
  RedisInstance("3.2.1", RedisInstance.SERVER, 7894),
  RedisInstance("3.2.1", RedisInstance.CLUSTER, Seq(7895, 7896, 7897))
)
```