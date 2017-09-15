package eu.monniot.redis.plugin

import java.util

import redis.embedded.PortProvider
import redis.embedded.ports.PredefinedPortProvider
import scala.collection.JavaConverters._

object RedisInstance {
  def apply(version: String, kind: String, ports: Seq[Int]) =
    new RedisInstance(version, kind, new PredefinedPortProvider(scalaToJava(ports)))

  def apply(version: String, kind: String, port: Int) =
    new RedisInstance(version, kind, new PredefinedPortProvider(scalaToJava(Seq(port))))

  private def scalaToJava(list: Seq[Int]): util.List[Integer] = {
    seqAsJavaList(list).asInstanceOf[util.List[Integer]]
  }

  val CLUSTER = "cluster"
  val SERVER = "server"
}

case class RedisInstance(version: String,
                         kind: String,
                         ports: PortProvider,
                         numOfMaster: Int = 3) {

  import RedisInstance._

  def isRedisCluster: Boolean = kind == CLUSTER

  def isRedisServer: Boolean = kind == SERVER
}