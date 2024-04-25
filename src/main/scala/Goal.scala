//package scala
package game

import scalafx.scene.paint.Color.*

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape.{FillRule, LineTo, MoveTo, Path}

// Goal is a box from which steering targets for enemies is randomly selected.
class Goal(var place: Vector2D):

  val side = 100
  val color = Color.web("#00FF0020")
  val targetSensitivity = 0.2

  // Is coordenates inside a goal.
  def isInsideGoal(xPos: Double, yPos: Double): Boolean =
    (xPos < place.x + side*targetSensitivity &&
    xPos > place.x &&
    yPos < place.y  + side*targetSensitivity &&
    yPos > place.y )

  // Draw goal box. (for debugging and map editing).
  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform
    g.translate(place.x, place.y)
    g.fillRect(0, 0, side, side)
    g.setTransform(oldTransform)

  end draw

end Goal