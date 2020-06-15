package competitors

import dragons.Dragon
import items.Item

case class Viking(stats: Stat,hunger: Double,item: Option[Item]){

  def this(stats: Stat) = this(stats,0.0,None)


  def speed = stats.speed
  def weight: Double = stats.weight
  def damage: Int = stats.damage
  def capacity: Double = stats.weight * 0.5 + stats.damage * 2

  def ride(dragon: Dragon):Option[Rider] = {
    if(dragon.canRide(this))
      Some(Rider(viking = this,dragon = dragon))
    else
      None
  }
}
