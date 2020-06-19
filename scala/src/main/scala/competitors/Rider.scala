package competitors

import competitions.Competition
import dragons.Dragon
import requirements.Requirement

case class Rider(viking: Viking, dragon: Dragon) extends
  Competitor(stats = Stat(damage = viking.damage + dragon.damage, weight = dragon.weight + viking.weight, speed = dragon.speed - viking.speed)){

  def capacity: Double = dragon.capacity - viking.weight

  override def meetRequirement(requirement: Requirement): Boolean = super.meetRequirement(requirement) && viking.meetRequirement(requirement)

  def compete(competition: Competition): Rider = {
    viking.compete(competition)
    this
  }
}

