package scala

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape.{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List


class Enemy():
end Enemy

//class Knight(var x: Double, var y: Double) extends Enemy():
class Knight(var speed: Vector2D, var place: Vector2D, var rotation: Double) extends Enemy():
  val hp    = 5
  //val speed = 5
  val defense = 5
  val size   = 6
  val color = Red
  // effects =

  var angle = 0.0

  val shapePoints = Buffer[(Double, Double)]()
  val shape =
    val angles = (0 to 20).map(_.toDouble / 5.0 * math.Pi)
    val radii = List.fill(10)(10)

    val xCoords =
      for (r, theta) <- (radii zip angles)
      yield r * math.cos(theta)

    val yCoords =
      for (r, theta) <- (radii zip angles)
      yield r * math.sin(theta)

    val polyline = Path()
    polyline.setFill(Color.Transparent)
    polyline.getElements.add(MoveTo(xCoords.head, yCoords.head))

    shapePoints.addOne((xCoords.head, yCoords.head))

    for (x, y) <- (xCoords.tail zip yCoords.tail) do
      polyline.getElements.add(LineTo(x, y))
      shapePoints.addOne((x, y))

    polyline
  end shape

  def move() =
    place = place + speed
    place = place.bound(worldWidth, sizeOfKnight, worldHeight, sizeOfKnight)
    angle += rotation

  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform

    g.translate(place.x, place.y)
    g.rotate(angle)
    g.fillPolygon(shapePoints.toSeq)

    g.setTransform(oldTransform)
  end draw

  /*
  def move() =
    this.x = this.x + 50
    this.y = this.y + 50
   */

end Knight


