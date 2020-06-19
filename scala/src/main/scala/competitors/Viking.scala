package competitors

import competitions.Competition
import dragons.Dragon
import items.{Item, Weapon}
import requirements._

abstract class Competitor(val stats: Stat){

  def speed: Int = stats.speed
  def weight: Double = stats.weight
  def damage: Int = stats.damage

  def capacity: Double
  def compete(competition: Competition): Competitor

  def meetRequirement(requirement: Requirement): Boolean = {
    requirement match {
      case MaxWeightRequirement(weight) => weight >= this.weight
      case MinCapacityRequirement(capacity) => capacity <= this.capacity
      case MaxDamageRequirement(damage) => damage >= this.damage
      case MinDamageRequirement(damage) => damage <= this.damage
      case MinWeightLiftRequirement(weight) => weight <= this.weight
      case _ => true
    }
  }
}

case class Viking(override val stats: Stat, var hunger: Double, item: Option[Item]) extends Competitor(stats){

  def this(stats: Stat,item: Option[Item]) = this(stats,0.0,item)
  def this(stats: Stat) = this(stats,0.0,None)

  def capacity: Double = stats.weight * 0.5 + stats.damage * 2

  override def meetRequirement(requirement: Requirement): Boolean =
    super.meetRequirement(requirement) && {
    requirement match {
      case ItemRequirement(item) => item.getClass.equals(this.item.getOrElse(None).getClass)
      case NotBeHungry(competition) => this.copy().compete(competition).hunger < 100
      case _ => true
    }
  }

  def ride(dragon: Dragon):Option[Rider] = {
    if(dragon.canRide(this))
      Some(Rider(viking = this.copy(),dragon = dragon))
    else
      None
  }

  def compete(competition: Competition): Viking = {
    this.hunger += competition.basicEfect
    this
  }

  def isBetter(otherViking: Viking, competition: Competition): Boolean = {
    this.copy().compete(competition) == competition.apply(List(this.copy(),otherViking.copy())).head
  }

}
