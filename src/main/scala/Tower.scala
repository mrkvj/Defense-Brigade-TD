package scala

import scalafx.scene.paint.Color.*

import scala.collection.mutable.Buffer
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.shape._//{FillRule, LineTo, MoveTo, Path}

import scala.collection.immutable.List

class Tower(x: Double, y: Double) {
  val range = 100
  var fireRate = 20
  var target: Option[Knight] = None // Change this to enemy

  def choseTarget() =
    target = Some(Game.enemies(0))


  def draw(g: GraphicsContext) =
    val oldTransform = g.getTransform

}

