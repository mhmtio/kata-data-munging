package io.terrafino.kata.datamunging

import scala.util.matching.Regex

class DataMunger {

  def readFile(filename: String, pattern: Regex): List[MinMaxSpread] = {
    val lines = scala.io.Source.fromResource(filename).getLines().toList
    lines.flatMap { line => processLine(line, pattern) }
  }

  def getMinSpreadFromFile(filename: String, pattern: Regex): MinMaxSpread =
    getMinSpread(readFile(filename, pattern))

  def getMinSpread(spreads: List[MinMaxSpread]): MinMaxSpread = {
    spreads.reduce { (s1, s2) => if (s1.spread < s2.spread) s1 else s2 }
  }

  def processLine(line: String, pattern: Regex): List[MinMaxSpread] = line match {
    case pattern(key, max, min) => List(MinMaxSpread(key, max.toInt, min.toInt))
    case _ => List.empty[MinMaxSpread]
  }
}

case class MinMaxSpread(key: String, min: Int, max: Int) {
  val spread: Int = math.abs(max - min)
}
