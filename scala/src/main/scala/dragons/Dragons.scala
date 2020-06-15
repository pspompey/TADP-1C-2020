package dragons

import competitors.{Stat, Viking}
import requirements.{DamageRequirement, Requirement, WeightRequirement}

sealed trait Dragon {
  val basicSeed: Int = 60
  val stats: Stat
  val requirements: List[Requirement] = List(WeightRequirement(capacity >=))
  lazy val capacity: Double = stats.weight * 0.2

  def damage: Int = stats.damage
  def weight: Double = stats.weight
  def speed: Int = stats.speed

  def canRide(viking: Viking): Boolean = requirements.forall(r => r.meetRequirement(viking))
  protected def allRequirements(requirements: List[Requirement]*): List[Requirement] ={
    requirements.fold(this.requirements)((list,requirements) => list ::: requirements)
  }
}


case class DeadlyNadder(override val weight: Double, listOfRequirements: List[Requirement]) extends Dragon{

  def this(weight:Double) = this(weight,List[Requirement]())

  lazy val stats: Stat = Stat(damage = 150,weight = weight, speed = basicSeed)
  override val requirements: List[Requirement] = super.allRequirements(List[Requirement](DamageRequirement(stats.damage >=)),listOfRequirements)
}


case class NightFury(override val damage:Int, override val weight: Double, listOfRequirements: List[Requirement]) extends Dragon{

  def this(damage: Int, weight:Double) = this(damage,weight,List[Requirement]())

  lazy val stats: Stat = Stat(damage = damage,weight = weight, speed = basicSeed * 3)
  override val requirements: List[Requirement] = super.allRequirements(listOfRequirements)

}


case class Gronckle(override val weight: Double, maxCapacity: Double, listOfRequirements: List[Requirement]) extends Dragon{

  def this(weight:Double,maxCapacity: Double) = this(weight,maxCapacity,List[Requirement]())

  lazy val stats: Stat = Stat(damage = (weight * 5).toInt, weight = weight, speed = basicSeed / 2)
  override val requirements: List[Requirement] = super.allRequirements(List[Requirement](WeightRequirement(maxCapacity >=)),listOfRequirements)
}
