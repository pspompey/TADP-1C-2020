package characters

abstract class Character(stats: Stat) {


  def capacity:Double
  def damage: Int = stats.damage
  def weight: Int = stats.weight
  def speed: Int = stats.speed
}
