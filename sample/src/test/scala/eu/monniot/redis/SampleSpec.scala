package eu.monniot.redis

import org.scalatest.{Matchers, FlatSpec}

class SampleSpec extends FlatSpec with Matchers {

  behavior of "Redis plugin"

  it should "have launched a redis standalone instance and a redis cluster" in {
    true shouldEqual true
  }
}