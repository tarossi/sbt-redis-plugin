package eu.monniot.redis.plugin

import sbt.SettingKey

/**
  * Created by francois on 27/01/17.
  */
trait RedisKeys {
  lazy val redisBinaries = SettingKey[Seq[((String, OS, Architecture), String)]](
    "redis-binaries",
    "A list of redis path associated with a version, os and arch"
  )

  lazy val redisInstances = SettingKey[Seq[RedisInstance]](
    "redis-instances",
    "A list of redis instances to start for tests"
  )
}

object RedisKeys extends RedisKeys