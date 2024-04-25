//package scala
package game

import game.Main.timer
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.*
import scalafx.scene.media.{Media, MediaPlayer}

import java.io.File
import scalafx.scene.text.Text
import scalafx.scene.text.Font
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.Includes.eventClosureWrapperWithZeroParam
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.util.Duration


// Main function. Game is run from here.
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
    val audioPath = "src/main/music/Tavern Idea.mp3"
    val file = new File(audioPath)
    val media = new Media(file.toURI.toString)
    val mediaPlayer = new MediaPlayer(media)
    mediaPlayer.setOnEndOfMedia(() => mediaPlayer.seek(mediaPlayer.getStartTime))

    // Tower movement, placement and mouse control.
    //var chosenTower: Option[Tower] = None
    var isMovingTower = false
    arena.onMousePressed = (event: MouseEvent) => {
      if isMovingTower then
        Game.chosenTower.foreach(tower =>
          tower.isMoving = false
          Game.towers += tower
          Game.chosenTower = None)
        isMovingTower = false
    }
    arena.onMouseMoved = (event: MouseEvent) => {
      if isMovingTower then
        Game.chosenTower.foreach { tower =>
          tower.moveTower(event.x, event.y)
          println(s"xcoord: ${event.x}, ycoord: ${event.y}")
        }
    }

    // General Buttons
    val startGameButton = new Button("Start Game") {
      layoutX = 100
      layoutY = 100
    }
    val endGameButton = new Button("End Game") {
      layoutX = 100
      layoutY = 900
    }
    val restartButton = new Button("Restart") {
      layoutX = 100
      layoutY = 100
    }
    val mainMenuButton = new Button("Main Menu") {
      layoutX = 100
      layoutY = 150
    }
    // Tower Buttons
    val buyBowButton = new Button(s"Bow: ${Game.towerPrices("Bow")}G") {
      layoutX = worldWidth - 200
      layoutY = 100
    }
    val buyMarksmanButton = new Button(s"Marksman: ${Game.towerPrices("Marksman")}G") {
      layoutX = worldWidth - 200
      layoutY = 150
    }
    val buyAutoCrossbowButton = new Button(s"Auto Crossbow: ${Game.towerPrices("Auto Crossbow")}G") {
      layoutX = worldWidth - 200
      layoutY = 200
    }

    // Theese need to be after buttons but before actions.
    // Triggers game over.
    def gameOver() =
      timer.stop()
      writeScoreToCSV(Game.player.score)
      mediaPlayer.stop()
      g.fill = Color.web("#80000040")
      g.fillRect(0, 0, worldWidth, worldHeight)
      root.children.clear()
      root.children += arena
      root.children += gameOverText
      root.children += restartButton
      root.children += highScoresText
      root.children += mainMenuButton
    // Triggers main menu.
    def mainMenu() =
      // General stuff
      timer.stop()
      writeScoreToCSV(Game.player.score)
      mediaPlayer.stop()
      g.fill = Color.web("#808080")
      g.fillRect(0, 0, worldWidth, worldHeight)
      root.children.clear()
      root.children += arena
      // Text
      root.children += mainMenuText
      root.children += highScoresText
      // Buttons
      root.children += startGameButton
      // TODO: Add help button and settings button
      //root.children += helpButton
      //root.children += settingsButton

    // TODO: Fix this. Works only on the first time.
    // 3 second timer for insufficient gold message.
    def notEnoughGold() =
      root.children += notEnoughGoldText
      val timeline = new Timeline {
        keyFrames = Seq(
          at(Duration(0)) {
            notEnoughGoldText.opacity -> 1.0
          },
          at(Duration(3000)) {
            notEnoughGoldText.opacity -> 0.0
          }
        )
        onFinished = () => stop()
      }
      timeline.play()

    // Actions
    val startGameAction = () => {
      // General stuff
      timer.start()
      mediaPlayer.play()
      Game.restartGame()
      root.children.clear()
      root.children += arena
      // Text
      //root.children -= gameOverText
      //root.children -= highScoresText
      root.children += gameInfoText
      root.children += purchaseTowersText
      // Buttons
      root.children += endGameButton
      root.children += buyBowButton
      root.children += buyMarksmanButton
      root.children += buyAutoCrossbowButton
    }
    val gameOverAction = () => {
      gameOver()
    }
    val mainMenuAction = () => {
      mainMenu()
    }
    // Buy towers.
    val buyBowAction = () => {
      val goldAfterPurchase = Game.player.gold - Game.towerPrices("Bow")
      if goldAfterPurchase >= 0 then
        isMovingTower = true
        Game.chosenTower = Some(new Bow(0, 0))
        Game.chosenTower.foreach(_.isMoving = true)
        Game.player.gold = goldAfterPurchase
      else
        notEnoughGold()
    }
    val buyMarksmanAction = () => {
      val goldAfterPurchase = Game.player.gold - Game.towerPrices("Marksman")
      if goldAfterPurchase >= 0 then
        isMovingTower = true
        Game.chosenTower = Some(new Marksman(0, 0))
        Game.chosenTower.foreach(_.isMoving = true)
        Game.player.gold = goldAfterPurchase
      else
        notEnoughGold()
    }
    val buyAutoCrossbowAction = () => {
      val goldAfterPurchase = Game.player.gold - Game.towerPrices("Auto Crossbow")
      if goldAfterPurchase >= 0 then
        isMovingTower = true
        Game.chosenTower = Some(new AutoCrossbow(0, 0))
        Game.chosenTower.foreach(_.isMoving = true)
        Game.player.gold = goldAfterPurchase
      else
        notEnoughGold()
    }

    // Buttons
    startGameButton.onAction = startGameAction
    restartButton.onAction = startGameAction
    endGameButton.onAction = gameOverAction
    mainMenuButton.onAction = mainMenuAction
    //Towers
    buyBowButton.onAction = buyBowAction
    buyMarksmanButton.onAction = buyMarksmanAction
    buyAutoCrossbowButton.onAction = buyAutoCrossbowAction

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
      Game.gameScreen match
        case "game" =>
          Game.step()
          paint()
          gameInfoText.text = Game.getGameInfo()
        case "main_menu" =>
          mainMenu()
        case "game_over" =>
          gameOver()
        case _ => throw new NonexistingGameScreenException("Impossible game screen reached. Check screen state logic. ")

    // Default Initializers
    root.children += arena
    //root.children += restartButton
    //root.children += highScoresText

    // Game animation timer
    timer = AnimationTimer(_ => repaint())
    if Game.player.lost then timer.stop()
    timer.start()

    // Scene
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

