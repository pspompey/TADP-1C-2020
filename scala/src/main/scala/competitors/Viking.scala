package competitors

import competitions.Competition
import dragons.Dragon
import items.{Item, Weapon}
import requirements._

case class Viking(stats: Stat, var hunger: Double,item: Option[Item]){

  def this(stats: Stat,item: Option[Item]) = this(stats,0.0,item)
  def this(stats: Stat) = this(stats,0.0,None)

  def speed: Int = stats.speed
  def weight: Double = stats.weight
  def damage: Int = stats.damage

  def capacity: Double = stats.weight * 0.5 + stats.damage * 2

  def meetRequirement(requirement: Requirement): Boolean = {
    requirement match {
      case MaxWeightRequirement(weight) => weight >= this.weight
      case MinCapacityRequirement(capacity) => capacity <= this.capacity
      case MaxDamageRequirement(damage) => damage >= this.damage
      case MinDamageRequirement(damage) => damage <= this.damage
      case ItemRequirement(item) => item.getClass.equals(this.item.getOrElse(None).getClass)
      case MinWeightLiftRequirement(weight) => weight <= this.weight
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
