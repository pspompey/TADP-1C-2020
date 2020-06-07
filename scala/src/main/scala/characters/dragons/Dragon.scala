package characters.dragons

import characters.{Character, Stat}
import characters.competitors.Viking
import requirements.{Requirement, WeightRequirement}

abstract class Dragon(val stats: Stat,val _requirements: List[Requirement]) extends Character(stats) {

  val requirements = new WeightRequirement(weightRequired = this.capacity >=) :: _requirements


  def this(stats: Stat) = this(stats,List[Requirement]())

  override def capacity:Double = stats.weight * 0.2
  override def speed: Int = stats.speed - stats.weight

  def canRide(viking: Viking): Boolean = requirements.forall(requirement => requirement.require(viking))

  protected def basicSpeed :Int = 60
}
