package competitors

import competitions.Competition
import dragons.Dragon
import items.{Item, Weapon}
import requirements._

trait Competitor{

  def speed: Int
  def weight: Double
  def damage: Int
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

case class Viking(stats: Stat, var hunger: Double,item: Option[Item]) extends Competitor{

  def this(stats: Stat,item: Option[Item]) = this(stats,0.0,item)
  def this(stats: Stat) = this(stats,0.0,None)

  override def speed: Int = stats.speed
  override def weight: Double = stats.weight
  override def damage: Int = stats.damage
  override def capacity: Double = stats.weight * 0.5 + stats.damage * 2

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

  override def compete(competition: Competition): Viking = {
    this.hunger += competition.basicEfect
    this
  }

  def isBetter(otherViking: Viking, competition: Competition): Boolean = {
    this.copy().compete(competition) == competition.apply(List(this.copy(),otherViking.copy())).head
  }

}
