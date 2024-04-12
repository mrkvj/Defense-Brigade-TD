package scala

import scala.collection.mutable.*
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

object Game:
  val player = Player()
  // Goals should be placed before enemies, because goals will be used to determine enimies movement.
  val goals = Buffer[Goal]()
  goals += Goal(Vector2D(440, 480 ))
  //goals += Goal(Vector2D(310, 350 ))
  goals += Goal(Vector2D(480, 220 ))
  goals += Goal(Vector2D(1040, 220 ))
  goals += Goal(Vector2D(1040, 1020 ))

  var enemies = Buffer[Knight]()
  enemies += Knight(Vector2D(0,0), Vector2D(100, 100), 0, goals)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 480), 0, goals)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 500), 0, goals)
  enemies += Knight(Vector2D(0.5,0), Vector2D(50, 520), 0, goals)
  enemies += Knight(Vector2D(0.5,0), Vector2D(500, 520), 0, goals)

  def step() =
    enemies.foreach(_.move())
    enemies.foreach(_.updateGoals())
    player.hp = player.hp - enemies.count(_.enemyGoalReached)
    enemies = enemies.filterNot(_.enemyGoalReached)

  //Game losing conditions
  def gameLost(): Boolean =
    player.hp <= 0

  def draw(g: GraphicsContext) =
    g.fill = Color.LightGreen
    goals foreach (_.draw(g))
    g.fill = Color.Red
    enemies foreach (_.draw(g))
    //if gameLost then DRAW LOSINGS
end Game