package characters.competitors

import characters.{Character, Stat}
import characters.dragons.Dragon

class Rider(viking: Viking, dragon: Dragon) extends Character(new Stat) {

  val stats: Stat = calculateStats(viking, dragon)

  override def capacity:Double = dragon.capacity - viking.weight

  def calculateStats(viking: Viking, dragon: Dragon): Stat = {
    val damage = viking.damage + dragon.damage
    val speed = dragon.speed - viking.weight

    Stat(damage = damage, weight = 0, speed = speed)
  }

}
