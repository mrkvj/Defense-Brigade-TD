//package scala
package game

import scala.collection.mutable.*
import upickle.default._
import scala.io._
import java.io._

private val path: os.Path = os.pwd/"src"/"main"/"scala"/"world_grid.json"

def gridToJSON(worldGrid: Array[Array[Square]]): Unit =
  val jsonStr: String = upickle.default.write(worldGrid)
  os.write(path, jsonStr)
end gridToJSON

def JSONToGrid(): Array[Array[Square]] =
  val worldGrid: Array[Array[Square]] = upickle.default.read[Array[Array[Square]]](os.read(path))
  worldGrid
end JSONToGrid

def writeScoreToCSV(newScore: Int) =
  val path = "/Users/veikkomarkkula/Code/DefenceBrigadeTD/src/main/scala/high_scores.csv"
  val file = Source.fromFile(path)

  var scores: Buffer[Int] = file.getLines.map(_.toInt).toBuffer
  scores += newScore
  var sortedScores = scores.sorted(Ordering[Int].reverse)
  sortedScores = sortedScores.take(50)

  val fileWriter = new BufferedWriter(new FileWriter(path))
  sortedScores.foreach(line =>
    fileWriter.write(line.toString)
    fileWriter.newLine()
  )
  fileWriter.close()

def readHighScoresFromCSV() =
  val path = "/Users/veikkomarkkula/Code/DefenceBrigadeTD/src/main/scala/high_scores.csv"
  val file = Source.fromFile(path)

  var scores: Buffer[Int] = file.getLines.map(_.toInt).toBuffer
  val sortedScores = scores.sorted(Ordering[Int].reverse)
  val topScores = sortedScores.take(10)
  val scoresString = "High Scores:\n" + topScores.mkString("\n")

  scoresString