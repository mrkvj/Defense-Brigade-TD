package scala

import upickle.default._
//import scala.io._
//import os._

// Scalafx drawing world grid
//import org.w3c.dom.css.RGBColor
//import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color.*

def worldGenerator(): Array[Array[Square]] =
  // Generates world grid and saves it to a file.
  val matrix = Array.ofDim[Square](worldWidth, worldHeight)
  for y <- 0 to squareHeight do
    for x <- 0 to squareWidth do
      matrix(x)(y) = new Square(x, y)
    end for
  end for
  matrix
end worldGenerator

def gridToJSON(worldGrid: Array[Array[Square]]): Unit =

  val path: os.Path = os.pwd/"src"/"main"/"scala"/"world_grid.json"
  val jsonStr: String = upickle.default.write(worldGrid)
  os.write(path, jsonStr)
end gridToJSON

def JSONToGrid(): Array[Array[Square]] =
  // Maybe add path to constants
  val path: os.Path = os.pwd/"src"/"main"/"scala"/"world_grid.json"
  val worldGrid: Array[Array[Square]] = upickle.default.read[Array[Array[Square]]](os.read(path))
  worldGrid
end JSONToGrid

def drawWorldGrid(worldGrid: Array[Array[Square]]): Scene =
  val root = Pane()
  val scene = Scene(parent = root)
  for j <- 0 to squareHeight do
    for i <- 0 to squareWidth do
      val square = new Rectangle:
        x = worldGrid(i)(j).x * squareDim      // .getX PRODUCES AN EXCEPTION FOR SOME REASON
        y = worldGrid(i)(j).y * squareDim
        width = squareDim
        height = squareDim
        fill = LightGreen   //worldGrid(i)(j).getColor
      root.children += square
    end for
  end for
  scene
end drawWorldGrid