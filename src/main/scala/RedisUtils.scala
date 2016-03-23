import redis.embedded.RedisExecProvider
import redis.embedded.cluster.RedisCluster
import sbt._

object RedisUtils {

  import redis.embedded.RedisServer

  private var redisServers: Seq[RedisServer] = null

  private var redisClusters: Seq[RedisCluster] = null

  def startRedisServer(logger: Logger, providers: Map[String, RedisExecProvider], redisList: Seq[RedisInstance]) = {
    redisServers = redisList.map { config =>

      val port = config.ports.next()

      val redisServer = new RedisServer.Builder()
        .redisExecProvider(providers(config.version))
        .port(port)
        .build()

      redisServer.start()

      logger.info(s"Redis Server started on port $port")

      redisServer
    }
  }

  def startRedisCluster(logger: Logger, providers: Map[String, RedisExecProvider], redis: Seq[RedisInstance]) = {
    redisClusters = redis.map { config =>

      logger.info("Starting Redis Cluster")

      val redisCluster = new RedisCluster.Builder()
        //        .serverPorts(config.ports)
        .withServerBuilder(new RedisServer.Builder().redisExecProvider(providers(config.version)))
        .build()

      redisCluster
    }
  }

  def stopRedisInstances(): Unit = {
    if (redisServers != null) {
      redisServers.foreach(_.stop())
    }
    if (redisClusters != null) {
      redisClusters.foreach(_.stop())
    }
  }
}
