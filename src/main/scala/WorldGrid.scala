package scala

import org.w3c.dom.css.RGBColor
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color.*

import upickle.default.*

case class Square(x_init: Int, y_init: Int):
  val x             = x_init
  val y             = y_init
  val width         = squareDim
  val height        = squareDim
  val high_ground   = true
  // var color: Color  = LightGreen      // FIX THIS

  //def getX: Int = x
  //def getY: Int = y

  //def getColor: Color= this.color     // FIX THIS

end Square

object Square:
  implicit val writer: Writer[Square] = macroRW
  implicit val reader: Reader[Square] = macroRW
end Square
