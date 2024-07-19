//package scala
package game

import scala.collection.mutable.*
import scalafx.scene.canvas.GraphicsContext

// Game object. Handles game state (game objects and player) during runtime.
object Game:
  // Variables
  var screenOptions = Vector[String]("main_menu", "game", "game_over")
  var gameScreen: String = screenOptions(0)
  var waveDifficulity = 5
  var waveNumber = 0
  var towerPrices = Map[String, Int]("Bow" -> 10, "Marksman" -> 20, "Auto Crossbow" -> 30)

  // Generate player.
  val player = Player()

  // Generate buffers for game objects.
  val goals = generateGoals()
  var enemies = Buffer[Enemy]()
  var chosenTower: Option[Tower] = None
  var projectiles = Buffer[Projectile]()
  var towers = Buffer[Tower]()

  // For debugging
  //var towers = generateTowers()
  //var enemies = generateEnemies()
  //var projectiles = generateProjectiles()

  // Change game state when animation timer ticks.
  def step() =
    // Change states of all game objects according to their behaviour (defined by intenal methods).
    enemies.foreach(_.move())
    enemies.foreach(_.updateGoals())
    towers.foreach(tower => tower.targetAndShoot())
    projectiles.foreach(_.move())
    projectiles = projectiles.filterNot(projectile => projectile.targetHit || projectile.outOfRange)
    player.hp = player.hp - enemies.count(_.enemyGoalReached)
    enemies = enemies.filterNot(_.enemyGoalReached)
    enemies = enemies.filterNot(_.hp <= 0)
    //chosenTower.foreach(
    //towers.filter(_.isPlaced == false).head.move()

    // Check if game can be continued.
    if isGameLost() then gameLost()

    // Start a new wave
    if enemies.isEmpty then
      waveNumber += 1
      waveDifficulity += waveNumber * 2 + 5
      enemies = generateWave(goals, waveDifficulity, waveNumber)

  // Check losing conditions
  def isGameLost(): Boolean =
    player.hp <= 0

  // Do when game is lost.
  def gameLost() =
    player.lost = true
    gameScreen = screenOptions(2)
    calculateFinalScore()
    //Main.resetGame()

  // Calculates final score.
  def calculateFinalScore() =
    player.score = (player.score + player.gold/2) * (1+waveNumber/10)

  // Game over screen.
  def gameOverScreen(): String =
    s"Game Over"

  // Game over text.
  def mainMenuText(): String =
    s"Main Menu"

  // Game runtime info.
  def getGameInfo(): String =
    // TIME
    s"Score:  ${player.score.toString} \n"+
    s"Gold:   ${player.gold} \n" +
    s"Health: ${player.hp} \n" +
    s"Wave:   ${waveNumber} \n"

  // Restarts the game to starting state.
  def restartGame() =
    gameReset()
    // Reset player
    player.reset()
    player.lost = false
    gameScreen = screenOptions(1)
    // Reset objects
    resetTimers()
    enemies = Buffer[Enemy]()
    towers = Buffer[Tower]()
    projectiles = Buffer[Projectile]()

  // Resets game runtime parameters.
  def gameReset() =
    waveDifficulity = 5
    waveNumber = 0

  // Resets sub timers of all game objects.
  def resetTimers() =
    towers.foreach(_.resetTimer = true)

  // Draw current state of all game objects at their current locations.
  def draw(g: GraphicsContext) =
    // TODO: Add option to show enemy goals.
    goals.foreach(goal =>
      g.fill = goal.color
      goal.draw(g))
    enemies.foreach(enemy =>
      g.fill = enemy.color
      enemy.draw(g))
    // TODO: Add option to show range or show if tower is chosen.
    //towers.foreach(tower =>
    //  g.fill = tower.rangeColor
    //  tower.drawTowerRange(g))
    towers.foreach(tower =>
      g.fill = tower.color
      tower.drawTower(g))
    chosenTower.foreach(tower =>
      g.fill = tower.rangeColor
      tower.drawTowerRange(g))
    chosenTower.foreach(tower =>
      g.fill = tower.color
      tower.drawTower(g))
    projectiles.foreach(projectile =>
      g.fill = projectile.color
      projectile.drawProjectile(g))

end Game