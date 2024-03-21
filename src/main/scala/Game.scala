package scala

import scala.collection.mutable.*
import scalafx.scene.canvas.GraphicsContext

object Game:
  //var enemy = Knight(50,500)

  val enemies = Buffer[Knight]()
  //enemies += Knight(Vector2D(0.5,0.5), Vector2D(100, 100), 0.002)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 480), 0)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 500), 0)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 520), 0)

  //def passTime() =
  //  enemy.move()
  def step() =
    enemies.foreach(_.move())

  def draw(g: GraphicsContext) = enemies foreach (_.draw(g))
end Game