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
  def hasItem(item: Item): Boolean
  def compete(competition: Competition): Viking
  def setHunger(hunger: Double): Competitor

}

case class Viking(override val stats: Stat, var hunger: Double, item: Option[Item], var team: Int = -1) extends Competitor(stats){

  def this(stats: Stat,item: Option[Item]) = this(stats,0.0,item)
  def this(stats: Stat) = this(stats,0.0,None)

  def capacity: Double = stats.weight * 0.5 + stats.damage * 2
  override def damage: Int = super.damage + item.map(_.damage).getOrElse(0)

  def hasItem(item: Item): Boolean = item.getClass.equals(this.item.getOrElse(None).getClass)

  def setHunger(hunger: Double): Viking = {
    this.hunger = hunger
    this
  }

  def joinTeam(team: Int): Viking = {
    this.team = team
    this
  }

  def ride(dragon: Dragon):Option[Rider] = {
    if(dragon.canRide(this))
      Some(Rider(viking = this.copy(), dragon = dragon.setAvailable(false)))
    else
      None
  }

  def compete(competition: Competition): Viking = {
    val viking = this.copy()
    viking.hunger += competition.basicEfect
    viking
  }

  def isBetter(otherViking: Viking, competition: Competition): Boolean = {
    this == competition.simulate(List(this.copy(),otherViking.copy())).head
  }

  def bestMount(dragons: List[Dragon], competition: Competition): Option[Competitor] = {
    competition.simulate(this :: dragons.flatMap(ride)).headOption
  }
}

