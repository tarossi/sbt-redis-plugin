package eu.monniot.redis.plugin

import redis.embedded.util.{OS => jOS}

/**
  * Correspond to the redis.embedded.util.OS enum, but scalaified to be automatically
  * imported in a sbt config file
  */
sealed trait OS {
  def toJava: jOS
}

object OS {

  object WINDOWS extends OS {
    def toJava: jOS = jOS.WINDOWS
  }

  object UNIX extends OS {
    def toJava: jOS = jOS.UNIX
  }

  object MAC_OS_X extends OS {
    def toJava: jOS = jOS.MAC_OS_X
  }

}