package scala

case class Vector2D(x: Double, y: Double):
  // Vectore description
  val length = math.hypot(x,y)
  val angle = math.atan2(y,x)

  // Create vector with revised angle ans scaled lenght
   def derive(angleOff: Double, lengthTimes: Double) =
    val newLen = length * lengthTimes
    val newAngle = angle + angleOff
    Vector2D(math.cos(newAngle) * newLen, math.sin(newAngle) * newLen)

  def +(other: Vector2D) = Vector2D(x + other.x, y + other.y)

  def bound(xBound: Int, shapeWidth: Int, yBound: Int, shapeHeight: Int) =
    val newX =
      if x >= xBound + shapeWidth then x - xBound - 2 * shapeWidth
      else if x < -shapeWidth then x + xBound + 2 * shapeWidth
      else x
    val newY =
      if y >= yBound + shapeHeight then y - yBound - 2 * shapeHeight
      else if y < -shapeHeight then y + yBound + 2 * shapeHeight
      else y

    if newX != x || newY != y then Vector2D(newX, newY)
    else this
  end bound

end Vector2D


