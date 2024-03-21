package scala

import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.shape.Rectangle

def worldGenerator(): Array[Array[Square]] =
  // Generates world grid and saves it to a file.
  val matrix = Array.ofDim[Square](worldWidth, worldHeight)
  for y <- 0 to squareHeight do
    for x <- 0 to squareWidth do
      val highGround = drawPath(x,y)      // Temporary before map editor
      matrix(x)(y) = new Square(x, y, highGround)
    end for
  end for
  matrix
end worldGenerator

// Temporary solution. Map editor replaces this.
def drawPath(x: Int,y: Int): Boolean =
  // currentSquare is true where path is drawn, hence inverse of currentSquare determinens if high or low ground.
  //val xCoords = Array[Int](10,10,20)
  //val yCoords = Array[Int](10,4,4)
  val normX: Double = (worldWidth/250.0) * (squareDim/5.0) /5.0
  val normY: Double = (worldHeight/250.0) * (squareDim/5.0) /5.0
  val xCoords = Array[Int](3*normX.toInt,3*normX.toInt,12*normX.toInt)
  val yCoords = Array[Int](7*normY.toInt,2*normY.toInt,2*normY.toInt)

  // Smoothing algorithm could be added

  def path(x: Int, c: Int): Boolean =
    var temp = false
    for i <- 1 to pathWidth do
      temp = temp | x == c | x == c + i - 1
    temp
  end path

  val currentSquare = (
    ( path(y,yCoords(0)) & (x<xCoords(0)+pathWidth-1) )                                 |   //1st corridor
      ( path(x,xCoords(0)+pathWidth-1) & (y<yCoords(0)+pathWidth)    & (y>yCoords(1)) )   |   //2nd corridor
      ( path(y,yCoords(1)+1) & (x>xCoords(1)+pathWidth-1)  & (x<xCoords(2)) )             |   //3rd corridor
      ( path(x,xCoords(2)) & (y>yCoords(2)))                                                  //4th corridor
    )
  //val currentSquare = (y==10) & (x<10) | (y<=10) & (y>4) & (x==10) | (y==5) & (x>=10)
  !currentSquare
end drawPath

def drawWorldGrid(worldGrid: Array[Array[Square]]): Scene =
  val root = Pane()
  val scene = Scene(parent = root)
  for j <- 0 to squareHeight do
    for i <- 0 to squareWidth do
      val square = new Rectangle:
        x = worldGrid(i)(j).x * squareDim
        y = worldGrid(i)(j).y * squareDim
        width = squareDim
        height = squareDim
        fill = worldGrid(i)(j).color
      root.children += square
    end for
  end for
  scene
end drawWorldGrid

/*
def drawEnemy(worldGrid: Array[Array[Square]]): Scene =

end drawEnemy
*/