package dragons

import competitors.{Stat, Viking}
import requirements._

sealed trait Dragon {
  val basicSeed: Int = 60
  val stats: Stat
  val basicRequirements: List[Requirement] = List(MaxWeightRequirement(capacity))
  val requirements: List[Requirement] = List(MaxWeightRequirement(capacity))
  lazy val capacity: Double = stats.weight * 0.2

  def damage: Int = stats.damage
  def weight: Double = stats.weight
  def speed: Int = stats.speed

  def canRide(viking: Viking): Boolean = requirements.forall(r => r(viking))

  protected def allRequirements(requirements: List[Requirement]*): List[Requirement] ={
    requirements.fold(this.basicRequirements)((list,requirements) => list ::: requirements)
  }
}


case class DeadlyNadder(override val weight: Double, listOfRequirements: List[Requirement]) extends Dragon{

  lazy val stats: Stat = Stat(damage = 150,weight = weight, speed = basicSeed)


  def this(weight:Double) = this(weight,List[Requirement]())

  override val requirements: List[Requirement] = super.allRequirements(List[Requirement](MaxDamageRequirement(stats.damage)),listOfRequirements)
}


case class NightFury(override val damage:Int, override val weight: Double, listOfRequirements: List[Requirement]) extends Dragon{

  lazy val stats: Stat = Stat(damage = damage,weight = weight, speed = basicSeed * 3)


  def this(damage: Int, weight:Double) = this(damage,weight,List[Requirement]())

  override val requirements: List[Requirement] = super.allRequirements(listOfRequirements)

}


case class Gronckle(override val weight: Double, maxCapacity: Double, listOfRequirements: List[Requirement]) extends Dragon{

  lazy val stats: Stat = Stat(damage = (weight * 5).toInt, weight = weight, speed = basicSeed / 2)


  def this(weight:Double,maxCapacity: Double) = this(weight,maxCapacity,List[Requirement]())

  override val requirements: List[Requirement] = super.allRequirements(List[Requirement](MaxWeightRequirement(maxCapacity)),listOfRequirements)
}
