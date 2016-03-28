import java.io.{BufferedReader, InputStreamReader}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import redis.embedded.{Redis, RedisExecProvider}
import redis.embedded.cluster.RedisCluster
import sbt._

object RedisUtils {

  import redis.embedded.RedisServer

  private var redisServers: Seq[RedisServer] = null

  private var redisClusters: Seq[RedisCluster] = null

  def startRedisServer(logger: Logger, providers: Map[String, RedisExecProvider], redisList: Seq[RedisInstance]) = {
    redisServers = redisList.map { config =>

      val port = config.ports.next()

      ensureFileExecutable(providers(config.version).get(), logger)

      val redisServer = new RedisServer.Builder()
        .redisExecProvider(providers(config.version))
        .port(port)
        .build()

      startAndCaptureErrors(redisServer, logger)

      logger.info(s"Redis Server started on port $port")

      redisServer
    }
  }

  def startRedisCluster(logger: Logger, providers: Map[String, RedisExecProvider], redis: Seq[RedisInstance]) = {
    redisClusters = redis.map { config =>

      logger.info("Starting Redis Cluster")

      ensureFileExecutable(providers(config.version).get(), logger)

      val redisCluster = new RedisCluster.Builder()
        .serverPorts(config.ports)
        .numOfMasters(config.numOfMaster)
        .withServerBuilder(new RedisServer.Builder().redisExecProvider(providers(config.version)))
        .build()

      startAndCaptureErrors(redisCluster, logger)

      logger.info(s"Redis Cluster started on ports ${redisCluster.ports()}")

      redisCluster
    }
  }

  def stopRedisInstances(): Unit = {
    // TODO Get access to a Logger here
//    logger.debug("Stopping redis instances")

    if (redisServers != null) {
      redisServers.foreach(_.stop())
    }
    if (redisClusters != null) {
      redisClusters.foreach(_.stop())
    }
  }

  private def ensureFileExecutable(file: File, logger: Logger) = {
    if (!file.canExecute) {
      logger.debug(s"Making ${file.getAbsolutePath} executable.")
      file.setExecutable(true, true)
    }
  }

  private def startAndCaptureErrors(redis: Redis, logger: Logger): Unit = {
    val reader = new BufferedReader(new InputStreamReader(redis.errors()))

    try {
      redis.start()
    } catch {
      case e: RuntimeException =>
        val ports = redis.ports()
        val error = Stream.continually(reader.readLine()).takeWhile(_ != null).foldLeft(false) { case (err, line) =>
          if (line.contains("Address already in use")) {
            logger.error(s"[${redis.getClass.getSimpleName}@$ports] $line")
            true
          } else false
        }

        if (error) throw e
    }
  }
}
