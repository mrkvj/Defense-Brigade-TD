package scala

// import scalafx.scene.paint.*
import scalafx.scene.paint.Color.*
import scala.util.*

import upickle.default.*

case class Square(x: Int, y: Int, highGroundInit: Boolean):
  //val x             = xInit
  //val y             = yInit
  val width         = squareDim
  val height        = squareDim
  val highGround    = highGroundInit
  var color         = determineColor
  //var color       = Color(.., .., .., ..)

  def determineColor =
    highGround match
      case true => SaddleBrown
      case _ => SandyBrown
  end determineColor

end Square

object Square:
  implicit val writer: Writer[Square] = macroRW
  implicit val reader: Reader[Square] = macroRW
end Square
