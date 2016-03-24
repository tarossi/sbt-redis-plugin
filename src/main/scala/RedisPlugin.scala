import redis.embedded.RedisExecProvider
import redis.embedded.util.{Architecture, OS}
import sbt.Keys._
import sbt._

object RedisPlugin extends AutoPlugin {

  import RedisKeys._

  override def trigger = allRequirements

  override def requires = plugins.JvmPlugin

  val autoImport = RedisKeys

  override lazy val projectSettings = defaultSettings

  def defaultSettings: Seq[Setting[_]] = Seq(
    redisInstances := Seq.empty,
    redisBinaries := Seq(
      ("3.0.7", OS.MAC_OS_X, Architecture.x86_64) -> getResourcePath("redis-server-3.0.7-darwin"),
      ("3.0.7", OS.UNIX, Architecture.x86_64) -> getResourcePath("redis-server-3.0.7")
    ),

    startRedis := effectivelyStartRedis(redisBinaries.value, redisInstances.value, streams.value.log),
    stopRedis := effectivelyStopRedis(streams.value.log),

    (test in Test) <<= stopRedis.dependsOn((test in Test).dependsOn(startRedis))
  )

  def getResourcePath(name: String): String = {
    val resource = getClass.getResource(name)
    if (resource == null) {
      // TODO Find a way to access a Logger from a SettingKey
      println(s"File is not in the classpath: $name")
      ""
    } else resource.getPath
  }

  def buildProvider(redisBinaries: Seq[((String, OS, Architecture), String)]) = {
    redisBinaries
      .map { case ((v, os, arch), path) =>
        (v, os, arch, path)
      }
      .groupBy(_._1)
      .map { case (v, list) =>
        val provider = RedisExecProvider.defaultProvider()

        list.foreach { case (_, os, arch, path) =>
          provider.`override`(os, arch, path)
        }

        (v, provider)
      }
  }

  def effectivelyStartRedis(redisBinaries: Seq[((String, OS, Architecture), String)], redis: Seq[RedisInstance], logger: Logger): Unit = {
    val redisExecProviders = buildProvider(redisBinaries)

    logger.info(s"Redis configuration: ${redisBinaries.toMap}")
    logger.info(s"Starting redis servers: $redis")

    RedisUtils.startRedisCluster(logger, redisExecProviders, redis.filter(m => m.isRedisCluster))
    RedisUtils.startRedisServer(logger, redisExecProviders, redis.filter(m => m.isRedisServer))
  }

  def effectivelyStopRedis(logger: Logger): Unit = {
    logger.info("Stopping redis instances")

    RedisUtils.stopRedisInstances()
  }
}
