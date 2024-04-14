//package scala
package game

import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape._
import scalafx.scene.media.{Media, MediaPlayer}
import java.io.File
import scalafx.scene.text.Text
import scalafx.scene.text.Font
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes.eventClosureWrapperWithZeroParam

object Main extends JFXApp3:

  var game = Game
  var timer: AnimationTimer = _
  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Defense Brigade TD"

    // Graphics
    val root = Pane()
    val arena = new Canvas(worldWidth, worldHeight)
    val g = arena.graphicsContext2D
    val textFields = new Text

    // Audio
    val audioPath = "/Users/veikkomarkkula/Code/DefenceBrigadeTD/src/main/scala/Tavern Idea.mp3"
    val file = new File(audioPath)
    val media = new Media(file.toURI.toString)
    val mediaPlayer = new MediaPlayer(media)
    mediaPlayer.setOnEndOfMedia(() => mediaPlayer.seek(mediaPlayer.getStartTime()))
    //mediaPlayer.play()

    // Text
    val gameInfo = new Text {
      text = "InitGameInfo"
      fill = Color.Black
      font = Font("Courier New", 24)
      layoutX = 50
      layoutY = 50
    }
    val highScores = new Text {
      text = readHighScoresFromCSV()
      fill = Color.Black
      font = Font("Courier New", 24)
      layoutX = 1300
      layoutY = 50
    }
    val gameOver = new Text {
      text = Game.gameOverScreen()
      fill = Color.Black
      font = Font("Courier New", 100)
      layoutX = 500
      layoutY = 500
    }
    // Button
    val button = new Button("Restart") {
      layoutX = 50
      layoutY = 900
      onAction = () => {
        Game.restartGame()
        root.children -= gameOver
        root.children.clear()
        root.children += arena
        root.children += gameInfo
        root.children += highScores
        timer.start()
        mediaPlayer.play()
      }
    }

    def paint() =
      // Clear
      g.clearRect(0, 0, worldWidth, worldHeight)
      // Paint world.
      for j <- 0 to squareHeight do
        for i <- 0 to squareWidth do
          val highGround = drawPath(i,j)
          if highGround then g.fill = Color.SaddleBrown
          else g.fill = Color.SandyBrown
          g.fillRect(i*squareDim,j*squareDim,squareDim,squareDim)
        end for
      end for
      Game.draw(g)
    end paint

    // Update drawn game objects.
    def repaint(): Unit =
      if !Game.player.lost then
        Game.step()
        //Game.drawText()
        paint()
        gameInfo.text = Game.getGameInfo()
      // else if game not started then Game.mainMenu()
      else
        //g.clearRect(0, 0, worldWidth, worldHeight)
        timer.stop()
        writeScoreToCSV(Game.player.score)
        mediaPlayer.stop()
        g.fill = Color.web("#80000040")
        g.fillRect(0, 0, worldWidth, worldHeight)
        root.children.clear()
        root.children += arena
        root.children += gameOver
        root.children += button


    end repaint

    // Timer
    timer = AnimationTimer(_ => repaint())
    if Game.player.lost then timer.stop()
    timer.start()


    if Game.player.lost then
      root.children += arena
      root.children += gameOver
      root.children += button
    else
      root.children += arena
      root.children += gameInfo
      root.children += highScores

    val scene = new Scene(parent = root)
    stage.scene = scene

  end start

end Main
    //////////////////////////////////////////

    //def resetGame() =


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

