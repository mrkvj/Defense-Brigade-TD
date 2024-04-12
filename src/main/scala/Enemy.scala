package scala

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape._//{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List


class Enemy():

end Enemy

//class Knight(var x: Double, var y: Double) extends Enemy():
class Knight(var speed: Vector2D, var place: Vector2D, var rotation: Double, var goals: Buffer[Goal]) extends Enemy():
  val hp: Int    = 5      // Double might offer more possiblities.
  var maxSpeed: Int = 10
  val defense: Int = 5
  val size: Int   = 20
  val color = Red
  var curGoal: Goal = goals.head
  var igoal: Int = 0
  var enemyGoalReached = false
  // effects =

  //var angle: Double = 0

  def move() =
    val xDiff = curGoal.place.x-place.x
    val yDiff = curGoal.place.y - place.y
    val diffLen = math.sqrt(xDiff*xDiff+yDiff*yDiff)
    val xDes = maxSpeed * xDiff/diffLen
    val yDes = maxSpeed * yDiff/diffLen
    place = place + Vector2D(xDes-speed.x, yDes-speed.y)

  //def steer(posE: (Double, Double) , posG: (Double, Double), velo: (Double, Double), maxSpeed: Double) =
  //  val posDiff = (posG._1-posE._1,posG._2-posE._2)
  //  val posDiffLen = math.sqrt(posDiff._1*posDiff._1 + posDiff._2*posDiff._2)
  //  val desVelo = (posDiff._1/posDiffLen, posDiff._2/posDiffLen)

  //  //val posDiffLen = math.sqrt(posDiff._1*posDiff._1 + posDiff._2*posDiff._2)
  //  //val desVelo = (maxSpeed*posDiff._1/(posDiffLen), maxSpeed * posDiff._2/(posDiffLen))
  //  //Vector2D(desVelo._1-1,desVelo._2-1)
  //  //Vector2D(1-velo._1,1-velo._2)
  //  Vector2D(desVelo._1-velo._1,desVelo._2-velo._2)

    //var posDiff = (10, 30)
    //var desVelo = (5*10/30, 5* 30/30)
    //Vector2D(desVelo._1-velo._1,desVelo._2-velo._2)
    //Vector2D(3,3)

  def updateGoals() =
    if goals.last.isInsideGoal(place.x, place.y) then enemyGoalReached = true
    else if curGoal.isInsideGoal(place.x, place.y) then
      igoal += 1
      curGoal = goals(igoal)

  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform

    g.translate(place.x, place.y)
    g.fillOval(0, 0, size, size)

    g.setTransform(oldTransform)

end Knight


