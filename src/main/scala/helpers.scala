//package scala
package game

import scala.collection.mutable.Buffer

// Various funcitons object generation.

// Degerates steering goals.
def generateGoals(): Buffer[Goal] =
  val goals = Buffer[Goal]()
    goals += Goal(Vector2D(180, 450))
  goals += Goal(Vector2D(400, 440))
  goals += Goal(Vector2D(420, 180))
    goals += Goal(Vector2D(650, 170))
  goals += Goal(Vector2D(980, 180))
    goals += Goal(Vector2D(990, 650))
  goals += Goal(Vector2D(990, 1100))
  goals


// Generates projectiles for debugging.
//def generateProjectiles(): Buffer[Projectile] =
//  var projectiles = Buffer[Projectile]()
//  projectiles += Arrow(Vector2D(400,500), Vector2D(0,0))
//  projectiles += Arrow(Vector2D(500,500), Vector2D(0,1000))
//  projectiles += Arrow(Vector2D(600,500), Vector2D(1000,0))
//  projectiles

// Generate towers (for debugging).
def generateTowers(): Buffer[Tower] =
  var towers = Buffer[Tower]()
  towers += Bow(600, 500)
  towers += AutoCrossbow(560, 340)
  towers += Bow(300, 400)
  towers += Marksman(700, 460)
  towers.foreach(_.isMoving = false)
  towers

// Generates predetermined wave (for debugging).
def generateStaticWave(goals: Buffer[Goal]): Buffer[Knight] =
  var enemies = Buffer[Knight]()
  enemies += Knight( Vector2D(100, 100), goals)
  enemies += Knight(Vector2D(50, 480), goals)
  enemies += Knight(Vector2D(50, 500), goals)
  enemies += Knight(Vector2D(50, 520), goals)
  enemies += Knight(Vector2D(500, 520), goals)
  enemies

// Generate wave that maches the waveDifficulty value.
def generateWave(goals: Buffer[Goal], waveDifficulty: Int, waveNumber: Int): Buffer[Enemy] =
  val startCoords = Vector2D(-100, 500)
  var enemies = Buffer[Enemy]()

  var currentDifficulty = 0
  // Loop until a random wave that maches the wave difficulty is created.
  while currentDifficulty < waveDifficulty do

    // Enemy randomizer
    val headroom = waveDifficulty - currentDifficulty
    val possibleEnemies = listOfEnemies.filter(enemy => enemy._2 <= headroom)
    val enemyType = possibleEnemies(random.nextInt(possibleEnemies.length))

    // Add random enemy to game.
    enemyType._1 match
    case "Peasant" =>
      enemies += Peasant(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
    case "Knight" =>
      enemies += Knight(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
    case "Wolf" =>
      enemies += Wolf(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
    case "Lizard" =>
      enemies += Lizard(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
    case _ => // This should not happen
      currentDifficulty = waveDifficulty

    currentDifficulty += enemyType._2
  end while

  enemies





//def generateWave(goals: Buffer[Goal], waveDifficulity: Int): Buffer[Enemy] =
//  // Startin coordinates for the wave.
//  val startCoords = Vector2D(-100, 500)
//
//  // Genearte wave.
//  var enemies = Buffer[Enemy]()
//  var currentDifficulity = 0
//  while currentDifficulity <= waveDifficulity do
//    //val headRoom = waveDifficulity - currentDifficulity
//    val enemyIndex = random.nextInt(listOfEnemies.length)
//    var enemy = Enemy(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
//    listOfEnemies(enemyIndex) match
//      case "Peasant" =>
//        enemy = Peasant(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
//      case "Knight" =>
//        enemy = Knight(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
//      case "Wolf" =>
//        enemy = Wolf(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
//      case "Lizard" =>
//        enemy = Lizard(Vector2D(startCoords.x+random.nextInt(101)-50, startCoords.y+random.nextInt(101)-50), goals)
//      case _ =>
//      currentDifficulity += enemy.difficulityScore
//      enemies += enemy
//  enemies

