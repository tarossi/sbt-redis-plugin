import redis.embedded.util.{Architecture, OS}
import sbt.{SettingKey, TaskKey, _}


object RedisKeys {
  lazy val redisBinaries = SettingKey[Seq[((String, OS, Architecture), String)]](
    "redis-binaries",
    "A list of redis path associated with a version, os and arch"
  )

  lazy val redisInstances = SettingKey[Seq[RedisInstance]](
    "redis-instances",
    "A list of redis instances to start for tests"
  )

  lazy val startRedis = TaskKey[Unit]("start-redis", "Start the different redis as per the defined configuration")
  lazy val stopRedis = TaskKey[Unit]("stop-redis", "Stop the different redis as per the defined configuration")
}
