package game

// This file is for global constants. 

// World settings
val worldWidth      = 1500
val worldHeight     = 1000
val squareDim       = 20
val squareWidth     = worldWidth/squareDim
val squareHeight    = worldHeight/squareDim

// Path settings (normalization on scaling could be added)
val pathWidth = 8
val goalSide = 80


// General settings
val worldEditor     = false
val createGrid      = true

// Enemy settings
val listOfEnemies: Vector[(String, Int)] = Vector(("Peasant", 1), ("Knight", 3), ("Wolf", 5), ("Lizard", 10))
val sizeOfKnight    = 5


// Random generation
