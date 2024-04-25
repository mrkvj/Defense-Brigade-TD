
package game

import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, Text}

// Text fields for UI.
val gameInfoText = new Text {
  text = "InitGameInfo"
  fill = Color.Black
  font = Font("Courier New", 24)
  layoutX = 50
  layoutY = 50
}

val purchaseTowersText = new Text {
  text = "Purchase Towers:"
  fill = Color.Black
  font = Font("Courier New", 18)
  layoutX = worldWidth - 200
  layoutY = 50
}

val notEnoughGoldText = new Text {
  text = "Not enough gold"
  fill = Color.Red
  font = Font("Courier New", 24)
  layoutX = worldWidth/2
  layoutY = worldHeight/2
}

val highScoresText = new Text {
  text = readHighScoresFromCSV()
  fill = Color.Black
  font = Font("Courier New", 24)
  layoutX = 1300
  layoutY = 50
}
val finalScoreText = new Text {
  text = Game.player.score.toString
  fill = Color.Black
  font = Font("Courier New", 50)
  layoutX = 500
  layoutY = 700
}

val gameOverText = new Text {
  text = Game.gameOverScreen()
  fill = Color.Black
  font = Font("Courier New", 100)
  layoutX = 500
  layoutY = 200
}

val mainMenuText = new Text {
  text = Game.mainMenuText()
  fill = Color.Black
  font = Font("Courier New", 100)
  layoutX = 500
  layoutY = 200

}
