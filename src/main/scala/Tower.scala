//package scala
package game

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape._//{FillRule, LineTo, MoveTo, Path}
import scala.collection.immutable.List
import java.util.{Timer, TimerTask}
//import scalafx.scene.input.mouseEvent
import scalafx.Includes.jfxSceneProperty2sfx

// Tower class with tower types as subclasses.
class Tower(x: Double, y: Double):
  var resetTimer = false
  var coords = Vector2D(x,y)
  val range = 200
  var fireRate = 1000 // ms
  var target: Option[Enemy] = None
  var side = 20
  var rangeColor = Color.web("#FF000020")
  val color = Color.web("#000000")
  var fireTimer: Option[Timer] = None
  var isMoving = false
  var experience = 0
  //var enemiesInRange = Buffer[Enemy]()

  def placeTower() =
    isMoving = false

  def isInRange(coords2: Vector2D): Boolean =
    coords.distanceFrom(coords2) <= range

  def decideTarget() =
    val enemiesInRange = Game.enemies.filter(enemy => isInRange(enemy.coords))
    if enemiesInRange.nonEmpty then
      target = Some(enemiesInRange.last)
      //Some(enemiesInRange.head)
    else
      target = None
      //None

  def targetAndShoot() =
    decideTarget()
    target match
      case Some(foundTarget) =>
        if isInRange(foundTarget.coords) && foundTarget.hp > 0 then
          if fireTimer.isEmpty then
            shoot(foundTarget)
        else stopTimer()
      case _ =>

  // Stop shooting timer when no targets are found.
  def stopTimer() =
    fireTimer.foreach(_.cancel())
    fireTimer = None

  // Shoot a projectile object at enemy.
  def shoot(foundTarget: Enemy) =
    fireTimer = Some(new Timer)
    fireTimer.get.scheduleAtFixedRate( new TimerTask{
    def run() =
      if isInRange(foundTarget.coords) && foundTarget.hp > 0 && !resetTimer then
        Game.projectiles += Arrow(Tower.this, foundTarget)
      else stopTimer()
    },0,fireRate)

  def isInsideTower(xPos: Double, yPos: Double): Boolean =
    (xPos < coords.x + side &&
     xPos > coords.x &&
     yPos < coords.y  + side &&
     yPos > coords.y)

  def moveTower(xPos: Double, yPos: Double) =
    coords = Vector2D(xPos, yPos)
    //isMoving = true

  def drawTowerRange(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(coords.x, coords.y)
    g.fillOval(-range, -range, range*2, range*2)
    g.setTransform(oldTransform)

  def drawTower(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(coords.x, coords.y)

    g.fillRect(-side/2, -side/2, side, side)
    g.setTransform(oldTransform)

class Bow(x: Double, y: Double)
  extends Tower(x,y):

  override val color = Color.web("#00FF00")

class AutoCrossbow(x: Double, y: Double)
  extends Tower(x,y):
  fireRate = 200
  override val range = 100
  override val color = Color.web("#FF0000")

class Marksman(x: Double, y: Double)
  extends Tower(x,y):
  fireRate = 2000
  override val range = 500

  override def shoot(foundTarget: Enemy) =
    fireTimer = Some(new Timer)
    fireTimer.get.scheduleAtFixedRate( new TimerTask{
      def run() =
        if isInRange(foundTarget.coords) && foundTarget.hp > 0 then
          Game.projectiles += Piercing(Marksman.this, foundTarget)
        else stopTimer()
    },0,fireRate)

  override val color = Color.web("#FF00FF")

class Mage(x: Double, y: Double)
  extends Tower(x,y):

// Homing missile
  override val color = Color.web("#0000FF")
