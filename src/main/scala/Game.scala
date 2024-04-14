//package scala
package game

import scala.collection.mutable.*
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.text.Font

object Game:
  var waveDifficulity = 5
  var waveNumber = 0

  val player = Player()
  // Generate stuff.
  val goals = generateGoals()
  var enemies = Buffer[Enemy]()
  var towers = generateTowers()
  var projectiles = Buffer[Projectile]()

  def step() =
    // Game steps
    enemies.foreach(_.move())
    enemies.foreach(_.updateGoals())
    towers.foreach(tower => tower.targetAndShoot())
    projectiles.foreach(_.move())
    projectiles = projectiles.filterNot(projectile => projectile.targetHit || projectile.outOfRange)
    player.hp = player.hp - enemies.count(_.enemyGoalReached)
    enemies = enemies.filterNot(_.enemyGoalReached)
    enemies = enemies.filterNot(_.hp <= 0)
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
    // Calculate end score (+ time bonus)
    //Main.resetGame()

  // Game over screen.
  def gameOverScreen(): String =
    s"Game Over"
    // Display game information.

  // Game runtime info.
  def getGameInfo(): String =
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
    // Reset objects
    resetTimers()
    enemies = Buffer[Enemy]()
    towers = generateTowers()
    projectiles = Buffer[Projectile]()

  // Resets game runtime parameters.
  def gameReset() =
    waveDifficulity = 5
    waveNumber = 0

  // Resets sub timers of all game objects.
  def resetTimers() =
    towers.foreach(_.resetTimer = true)

  // Draw current state of all game objects.
  def draw(g: GraphicsContext) =
    //g.fill =
    towers.foreach(tower =>
      g.fill = tower.rangeColor
      tower.drawTowerRange(g))
    goals.foreach(goal =>
      g.fill = goal.color
      goal.draw(g))
    enemies.foreach(enemy =>
      g.fill = enemy.color
      enemy.draw(g))
    towers.foreach(tower =>
      g.fill = tower.color
      tower.drawTower(g))
    projectiles.foreach(projectile =>
      g.fill = projectile.color
      projectile.drawProjectile(g))

end Game