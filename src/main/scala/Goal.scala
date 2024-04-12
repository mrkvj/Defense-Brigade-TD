package scala

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape.{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List
class Goal(var place: Vector2D):

  val side = goalSide
  val color = LightGreen


  def isInsideGoal(xPos: Double, yPos: Double): Boolean =
    (xPos < this.place.x + side &&
    xPos > this.place.x - side &&
    yPos < this.place.y + side &&
    yPos > this.place.y - side)

  def draw(g: GraphicsContext) =
    //val oldTransform = g.getTransform

    //g.translate(place.x, place.y)
    //g.fillRect(side, side, side, side)
    g.fillRect(place.x-(side/2), place.y-(side/2), side, side)


    //g.setTransform(oldTransform)

  end draw

end Goal