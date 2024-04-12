package scala

import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape._

object Main extends JFXApp3:

    //private val worldWidth = 1500
    //private val worldHeight = 1000

    //private val pixelSize = 10



  var game = Game
  def start() =


    stage = new JFXApp3.PrimaryStage:
      title = "Defense Brigade TD"

    val root = Pane()

    val arena = new Canvas(worldWidth, worldHeight):

    end arena

    val g = arena.graphicsContext2D

    def paint() =

      g.clearRect(0, 0, worldWidth, worldHeight)

      for j <- 0 to squareHeight do
        for i <- 0 to squareWidth do
          val highGround = drawPath(i,j)
          if highGround then g.fill = Color.SaddleBrown
          else g.fill = Color.SandyBrown
          g.fillRect(i*squareDim,j*squareDim,squareDim,squareDim)
        end for
      end for

      //g.fill = Color.Green
      Game.draw(g)

    end paint

    def repaint() =
      Game.step()
      paint()
    end repaint

    val timer = AnimationTimer(_ => repaint())
    timer.start()

//////////////////////////////////////////

    root.children += arena

    val rectangle = new Rectangle:
      x = 275
      y = 175
      width = squareDim
      height = squareDim
      fill = Color.Blue

    root.children += rectangle
    /*
    val worldGrid = worldGenerator()

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
    */
    val scene = new Scene(parent = root)
    stage.scene = scene

    // Creates and save new worldGrid into a JSON file.

    /*
    if createGrid == true then
      val worldGrid = worldGenerator()
      gridToJSON(worldGrid)
    else
      val worldGrid = JSONToGrid()
      drawWorldGrid(worldGrid)
    //root.children += drawWorldGrid(worldGrid)
    */

  end start
end Main
