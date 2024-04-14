//package scala
package game


import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape._//{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List
import scala.util.Random

val random = new Random()

class Enemy(var coords: Vector2D, val goals: Buffer[Goal]):

  // Stats
  val defense: Int = 100
  val maxHp: Double = 100
  var hp: Double = 100      // Double might offer more possiblities.
  var maxSpeed: Double = 100
  var speed = calculateSpeed(0,0)
  var effects: Option[String] = None
  var difficulityScore = 1
  val scoreValue = 1
  val goldValue = 2

  //Goal stuff
  var igoal: Int = 0
  var curGoal: Goal = goals.head
  var targetCoords = targetRandomizer()
  var enemyGoalReached = false
  val pathSmoothing = 0.015

  // Appearance
  var color = Color.web("#008080")
  val size: Int   = 100

  def targetRandomizer() =
    Vector2D(curGoal.place.x + random.nextInt(curGoal.side), curGoal.place.y + random.nextInt(curGoal.side))

  def calculateSpeed(x: Double, y: Double): Vector2D =
    val spdLen = math.sqrt(x*x+y*y)
    if spdLen == 0 then Vector2D(0,0)
    else Vector2D(maxSpeed*x/spdLen, maxSpeed*x/spdLen)

  // Smooth steering.
  def move() =
    // Calculate target/enemy difference.
    val diff = targetCoords - coords
    val diffLen = diff.length
    // Calculate desired velocity
    val desVel = diff / diffLen * maxSpeed
    // Calculate steering force
    val steer = desVel - speed
    val smoothSteer = steer * pathSmoothing
    // Update speed and position
    speed = speed + smoothSteer
    coords = coords + speed

  def updateGoals() =
    if isCloseTarget && curGoal == goals.last then
      enemyGoalReached = true
    else if isCloseTarget then
      igoal += 1
      curGoal = goals(igoal)
      targetCoords = targetRandomizer()

  def fadeToWhite(dmg: Double) =
    val increment = 100*dmg/maxHp
    color = Color.rgb(
      (color.red*255+increment).toInt.min(255),
      (color.green*255+increment).toInt.min(255),
      (color.blue*255+increment).toInt.min(255)
    )

  def isHit(projectile: Projectile): Boolean =
    if projectile.coords.distanceFrom(coords) <= size/2 then
      hp -= projectile.dmg
      fadeToWhite(projectile.dmg)
      if hp <= 0 then
        Game.player.score += scoreValue
        Game.player.gold += goldValue
      true
    else
      false

  def isCloseTarget: Boolean =
    coords.x < targetCoords.x + curGoal.side/2 * curGoal.targetSensitivity &&
    coords.x > targetCoords.x - curGoal.side/2 * curGoal.targetSensitivity &&
    coords.y < targetCoords.y + curGoal.side/2 * curGoal.targetSensitivity &&
    coords.y > targetCoords.y - curGoal.side/2 * curGoal.targetSensitivity

  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(coords.x, coords.y)
    g.fillOval(-size/2, -size/2, size, size)
    g.setTransform(oldTransform)

class Peasant(var peasantCoords: Vector2D, override val goals: Buffer[Goal])
  extends Enemy(peasantCoords, goals):

  // Stats
  override val maxHp: Double = 3
  hp = 3
  maxSpeed = 1
  speed = calculateSpeed(0,0)
  difficulityScore = 1
  override val defense: Int = 1

  // Appearance
  color = Color.web("#808000")
  override val size = 15


class Knight(var knightCoords: Vector2D, override val goals: Buffer[Goal])
  extends Enemy(knightCoords, goals):

  // Stats
  override val maxHp: Double = 10
  hp = 10
  maxSpeed = 1
  speed = calculateSpeed(0,0)
  difficulityScore = 5
  override val defense: Int = 3

  // Appearance
  color = Color.web("#800000")
  override val size = 15

class Wolf(var wolfCoords: Vector2D, override val goals: Buffer[Goal])
  extends Enemy(wolfCoords, goals):

  override val maxHp: Double = 15
  hp = maxHp
  maxSpeed = 1
  //override val pathSmoothing = 0.05
  speed = calculateSpeed(0,0)
  difficulityScore = 10
  override val defense: Int = 1

  // Appearance
  color = Color.web("#000080")
  override val size = 20


class Lizard(var lizardCoords: Vector2D, override val goals: Buffer[Goal])
  extends Enemy(lizardCoords, goals):

  override val maxHp: Double = 40
  //override val pathSmoothing = 0.05
  hp = 20
  maxSpeed = 1
  speed = calculateSpeed(0,0)
  difficulityScore = 20
  override val defense: Int = 5

  // Appearance
  color = Color.web("#008000")
  override val size = 20

