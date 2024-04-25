package scala

// Player class.
class Player {
  var hp: Int = 5
  var gold: Int = 0
  var score: Int = 0
  var lost = true

  def reset() =
    hp = 5
    gold = 30
    score = 0
    lost = true
}
