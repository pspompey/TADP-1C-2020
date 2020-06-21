package competitors

import competitions.Competition
import dragons.Dragon
import requirements.Requirement

case class Rider(viking: Viking, dragon: Dragon) extends
  Competitor(stats = Stat(damage = viking.damage + dragon.damage, weight = dragon.weight + viking.weight, speed = dragon.speed - viking.weight.toInt)){

  def capacity: Double = dragon.capacity - viking.weight

  override def meetRequirement(requirement: Requirement): Boolean = super.meetRequirement(requirement) && viking.meetRequirement(requirement)

  def compete(competition: Competition): Viking = {
    val viking = this.viking.copy()
    viking.hunger += 5
    viking
  }

  def setHunger(hunger: Double): Rider = {
    this.viking.setHunger(hunger)
    this
  }
}

