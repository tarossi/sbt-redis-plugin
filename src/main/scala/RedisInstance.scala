import java.util

import redis.embedded.PortProvider
import redis.embedded.ports.PredefinedPortProvider
import scala.collection.JavaConversions._

object RedisInstance {
  def apply(version: String, kind: String, ports: Seq[Int]) =
    new RedisInstance(version, kind, new PredefinedPortProvider(scalaToJava(ports)))

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

  def isRedisCluster = kind == CLUSTER

  def isRedisServer = kind == SERVER
}