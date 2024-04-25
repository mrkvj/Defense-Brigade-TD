//package scala
package game

import scala.collection.mutable.*
import upickle.default._
import scala.io._
import java.io._

private val path: os.Path = os.pwd/"src"/"main"/"scala"/"world_grid.json"


// Unused for now. JSON writer.
def gridToJSON(worldGrid: Array[Array[Square]]): Unit =
  val jsonStr: String = upickle.default.write(worldGrid)
  os.write(path, jsonStr)
end gridToJSON

// Unused for now. JSON reader.
def JSONToGrid(): Array[Array[Square]] =
  val worldGrid: Array[Array[Square]] = upickle.default.read[Array[Array[Square]]](os.read(path))
  worldGrid
end JSONToGrid


// Write sorted scores to CSV file (max lines 50)
def writeScoreToCSV(newScore: Int) =
  val path = "src/main/game_files/high_scores.csv"
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

// Read sorted data from csv and pass 10 largest values as a string.
def readHighScoresFromCSV() =
  val path = "src/main/game_files/high_scores.csv"
  val file = Source.fromFile(path)

  var scores: Buffer[Int] = file.getLines.map(_.toInt).toBuffer
  val sortedScores = scores.sorted(Ordering[Int].reverse)
  val topScores = sortedScores.take(10)
  val scoresString = "High Scores:\n" + topScores.mkString("\n")
  scoresString