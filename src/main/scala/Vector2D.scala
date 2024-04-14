package scala

case class Vector2D(x: Double, y: Double):

  def length = math.sqrt(this.x*this.x+this.y*this.y)

  def distanceFrom(coords: Vector2D): Double =
    Vector2D(math.abs(this.x-coords.x), math.abs(this.y-coords.y)).length

  def calculateSpeed(maxSpeed: Int): Vector2D =
    val spdLen = math.sqrt(x*x+y*y)
    if spdLen == 0 then Vector2D(0,0)
    else Vector2D(maxSpeed*x/spdLen, maxSpeed*x/spdLen)

  def +(other: Vector2D) = Vector2D(x + other.x, y + other.y)

  def -(other: Vector2D) = Vector2D(x - other.x, y - other.y)

  def *(other: Double) = Vector2D(x * other, y * other)

  def /(other: Double) = Vector2D(x / other, y / other)

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

end Vector2D


