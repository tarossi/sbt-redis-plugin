# sbt-redis-plugin [![Build Status](https://travis-ci.org/fmonniot/sbt-redis-plugin.svg?branch=master)](https://travis-ci.org/fmonniot/sbt-redis-plugin)

_Coming Soon_

## Usage

Add this snippet to your `project/plugins.sbt`

```scala
resolvers += Resolver.url("fmonniot", url("https://dl.bintray.com/fmonniot/sbt-plugins"))(Resolver.ivyStylePatterns)

addSbtPlugin("eu.monniot.redis" % "redis-plugin" % "0.1.1")
```

You can now configure the plugin:

```scala
import redis.embedded.util.{Architecture, OS}

// [Optional] Configure a redis binary matrix
redisBinaries := Seq(
  (("3.0.7", OS.UNIX, Architecture.x86_64), "/path/to/the/correct/redis-server")
)

// Launch a server and a cluster
redisInstances := Seq(
  RedisInstance("3.0.7", RedisInstance.SERVER, Seq(7894)),
  RedisInstance("3.0.7", RedisInstance.CLUSTER, Seq(7895, 7896, 7897))
)
```