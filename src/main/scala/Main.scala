package scala

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._

object Main extends JFXApp3:

    //private val worldWidth = 1500
    //private val worldHeight = 1000

    //private val pixelSize = 10

  def start() =

    stage = new JFXApp3.PrimaryStage:
      title = "Defense Brigade TD"
      width = worldWidth
      height = worldHeight

    val root = Pane()

    val scene = Scene(parent = root)
    stage.scene = scene

    // Creates and save new worldGrid into a JSON file.
    val worldGrid = worldGenerator()

    /*
    if createGrid == true then
      val worldGrid = worldGenerator()
      gridToJSON(worldGrid)
    else
      val worldGrid = JSONToGrid()
      drawWorldGrid(worldGrid)
    //root.children += drawWorldGrid(worldGrid)
    */

    //val root = Pane()
    //val scene = Scene(parent = root)
    for j <- 0 to squareHeight do
      for i <- 0 to squareWidth do
        val square = new Rectangle:
          x = worldGrid(i)(j).x * squareDim
          y = worldGrid(i)(j).y * squareDim
          width = squareDim
          height = squareDim
          fill = worldGrid(i)(j).color
        root.children += square
      end for
    end for

    val rectangle = new Rectangle:
        x = 275
        y = 175
        width = squareDim
        height = squareDim
        fill = Blue
    //root.children += background
    root.children += rectangle

  end start
end Main
