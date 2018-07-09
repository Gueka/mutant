package com.gueka.mutant

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import java.util.Date
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit

class PerformanceTest extends Simulation {

  private val baseUrl = "http://localhost:8080"
  private val endpoint = "/mutant"
  private val contentType = "application/json"
  private val requestCount = 4

  private val simUsers = System.getProperty("SIM_USERS", "1").toInt

  private val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("application/json;charset=UTF-8")

  private val isMutantTest = repeat(requestCount) {
    exec(http("request_1")
      .post(endpoint)
      .header("Content-Type", contentType)
      .body(StringBody(
        s"""
           | {
           |  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
           | }
         """.stripMargin)).asJSON
         .check(status.is(200)))
  }
  private val scn = scenario("BootLoadSimulation")
    .exec(isMutantTest)

  setUp(
      scn.inject(rampUsers(500) over (20 seconds))
  ).protocols(httpConf)
  
}