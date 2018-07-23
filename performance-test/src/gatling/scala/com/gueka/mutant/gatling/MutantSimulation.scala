package com.gueka.mutant.gatling

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class MutantSimulation extends Simulation {
  
  val feeder = Array(
    Map("RandomDna" ->  """["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]""", "status" -> 200),
    Map("RandomDna" ->  """["AAGGTT","AAGGTT","TTCCAA","TTCCAA","AAGGTT","AAGGTT"]""", "status" -> 502)
  ).random

  val plainHeaders = Map(
    "Accept" -> """application/json""",
    "Content-Type" -> """application/json""")
  
  val httpConf = http.baseURL("http://localhost:8080/")

  val scnMutant = scenario("Mutant Test Simulation")
  .feed(feeder)
  .exec(
      http("is mutant")
      .post("mutant")
      .body(StringBody("""{ "dna": ${RandomDna} }""")).asJSON
      .check( status.is("${status}") )
      .headers(plainHeaders)
  )

  setUp(
      //scnMutant.inject(heavisideUsers(10000) over(10 seconds))
      scnMutant.inject(rampUsersPerSec(100) to 200 during(10 minutes))
  ).protocols(
        httpConf
  )

}