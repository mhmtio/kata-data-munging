package io.terrafino.kata.datamunging

import org.scalatest.{FunSuite, Matchers}

class DataMungerTest extends FunSuite with Matchers {

  private val WeatherDataPattern = """^ *(\d+) +(\d+)\*? +(\d+)\*? +.*$""".r
  private val SoccerDataPattern = """^ *\d+. (\S+) .* (\d+)  -  (\d+) .*$""".r

  val dm = new DataMunger()

  test("Should return the min spread of a single-item list of MinMaxSpreads") {
    val item = MinMaxSpread("1", 100, 100)
    val spreads = List(item)
    val minSpread = dm.getMinSpread(spreads)
    minSpread shouldBe item
  }

  test("Should return the min spread of a two-item list of MinMaxSpreads") {
    val item1 = MinMaxSpread("1", 100, 1)
    val item2 = MinMaxSpread("2", 100, 100)
    val spreads = List(item1, item2)
    val minSpread = dm.getMinSpread(spreads)
    minSpread shouldBe item2
  }

  test("Should return the correct day with min spread of testWeather.dat") {
    val minSpread = dm.getMinSpreadFromFile("testWeather.dat", WeatherDataPattern)
    minSpread.key shouldBe "2"
  }

  test("Should return a list of 30 MinMaxSpreads for weather.dat") {
    val spreads = dm.readFile("weather.dat", WeatherDataPattern)
    spreads should have size 30
  }

  test("Should return the correct day with min spread of weather.dat") {
    val minSpread = dm.getMinSpreadFromFile("weather.dat", WeatherDataPattern)
    minSpread.key shouldBe "14"
  }

  test("Should return the correct team with min spread of testFootball.dat") {
    val minSpread = dm.getMinSpreadFromFile("testFootball.dat", SoccerDataPattern)
    minSpread.key shouldBe "Liverpool"
  }

  test("Should return a list of 20 MinMaxSpreads for football.dat") {
    val spreads = dm.readFile("football.dat", SoccerDataPattern)
    spreads should have size 20
  }

  test("Should return the correct team with min spread of football.dat") {
    val minSpread = dm.getMinSpreadFromFile("football.dat", SoccerDataPattern)
    minSpread.key shouldBe "Aston_Villa"
  }

}
