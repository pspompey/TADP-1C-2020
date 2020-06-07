package characters.competitors

import exceptions.CantRideException
import items.Item
import characters.{Character, Stat}
import characters.dragons.Dragon

case class Viking(stats: Stat,hunger: Double,_item: Item) extends Character(stats) {

  require(hunger >= 0.0 && hunger <= 1.0)
  def this(stats: Stat,item:Item) = this(stats,0.0,item)
  def this(stats:Stat) = this(stats,0.0,null)

  override def capacity: Double = this.weight / 2 + this.damage * 2

  def rideDragon(dragon: Dragon):Rider = {
      if (dragon.canRide(this)) {
        new Rider(viking = this,dragon = dragon)
      }
      else {
        throw new CantRideException
      }
  }

  def item :String = _item.itemType
}
