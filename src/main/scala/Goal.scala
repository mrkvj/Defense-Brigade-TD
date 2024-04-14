//package scala
package game

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape.{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List

class Goal(var place: Vector2D):

  val side = 100
  val color = Color.web("#00FF0020")
  val targetSensitivity = 0.2

  def isInsideGoal(xPos: Double, yPos: Double): Boolean =
    (xPos < place.x + side*targetSensitivity &&
    xPos > place.x &&
    yPos < place.y  + side*targetSensitivity &&
    yPos > place.y )

  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(place.x, place.y)
    g.fillRect(0, 0, side, side)
    g.setTransform(oldTransform)

  end draw

end Goal