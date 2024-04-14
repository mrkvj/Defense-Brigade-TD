//package scala
package game

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scala.collection.mutable.Buffer

abstract class Projectile(tower: Tower, var target: Enemy):
  var coords = tower.coords
  val targetCoords = target.coords
  val color = Color.web("#000000")
  var dmg = 1
  val size: Double = 3
  val maxSpeed: Double = 1
  var targetHit = false
  var outOfRange = false

  var speed = predictiveAiming()

  // Predict enemy location and return speed vector accordingly.
  def predictiveAiming(): Vector2D

  def move(): Unit

  def drawProjectile(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(coords.x, coords.y)
    g.fillOval(0, 0, size, size)
    g.setTransform(oldTransform)

class Arrow(tower: Tower, var arrowTarget: Enemy)
  extends Projectile(tower, arrowTarget ):
  dmg = 2
  override val maxSpeed: Double = 10
  speed = predictiveAiming()
  override val size = 5

  def predictiveAiming() =
    val diff = (
      targetCoords +
        target.speed * targetCoords.distanceFrom(coords) / maxSpeed -
        coords)
    //val diff = Vector2D(targetCoords.x - coords.x, targetCoords.y - coords.y)
    val diffLen = Vector2D(diff.x, diff.y).length
    Vector2D(maxSpeed * diff.x / diffLen, maxSpeed * diff.y / diffLen)

  def move() =
    Game.enemies.foreach(enemy =>
      if enemy.isHit(this) then
        targetHit = true
        tower.experience = tower.experience + dmg
        //arrowTarget = enemy
    )
    //if arrowTarget.isHit(this) then targetHit = true
    if !tower.isInRange(coords) then outOfRange = true
    else coords = coords + speed

class Piercing(tower: Tower, var piercingTarget: Enemy)
  extends Projectile(tower, piercingTarget):

  override val maxSpeed: Double = 20
  dmg = 2
  speed = predictiveAiming()
  override val size = 3
  var hitCounter = 3

  def predictiveAiming() =
    val diff = (
      targetCoords +
        target.speed * targetCoords.distanceFrom(coords) / maxSpeed -
        coords)
    //val diff = Vector2D(targetCoords.x - coords.x, targetCoords.y - coords.y)
    val diffLen = Vector2D(diff.x, diff.y).length
    Vector2D(maxSpeed * diff.x / diffLen, maxSpeed * diff.y / diffLen)

  def move() =
    Game.enemies.foreach(enemy =>
      if enemy.isHit(this) then
        hitCounter -= 1
        tower.experience = tower.experience + dmg
    )
    if hitCounter < 0 then
      targetHit = true
    else if !tower.isInRange(coords) then outOfRange = true
    else coords = coords + speed

//class Fireball(tower: Tower, target: Enemy)
//  extends Projectile(tower, target):
//
//  override val size = 10


