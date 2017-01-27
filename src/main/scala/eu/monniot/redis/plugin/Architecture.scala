package eu.monniot.redis.plugin

import redis.embedded.util.{Architecture => jArch}

/**
  * Correspond to the redis.embedded.util.Architecture enum, but scalaified to be automatically
  * imported in a sbt config file
  */
sealed trait Architecture {
  def toJava: jArch
}

object Architecture {

  object x86 extends Architecture {
    def toJava: jArch = jArch.x86
  }

  object x86_64 extends Architecture {
    def toJava: jArch = jArch.x86_64
  }

}
