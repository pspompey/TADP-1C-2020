package competitors

import dragons.Dragon
import items.{Item, Weapon}
import requirements._

case class Viking(stats: Stat,hunger: Double,item: Option[Item]){

  def this(stats: Stat,item: Option[Item]) = this(stats,0.0,item)
  def this(stats: Stat) = this(stats,0.0,None)

  def speed = stats.speed
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
      case _ => true
    }
  }

  def ride(dragon: Dragon):Option[Rider] = {
    if(dragon.canRide(this))
      Some(Rider(viking = this.copy(),dragon = dragon))
    else
      None
  }
}