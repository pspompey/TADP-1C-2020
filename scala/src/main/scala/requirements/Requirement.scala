package requirements

import competitions.Competition
import competitors.Viking
import items.Item

trait Requirement extends (Viking => Boolean){
  def apply(vikingo: Viking): Boolean = vikingo.meetRequirement(this)
}

case class MaxWeightRequirement(weight: Double) extends Requirement
case class MinCapacityRequirement(capacity: Double) extends Requirement
case class MinDamageRequirement(damage: Int) extends Requirement
case class MaxDamageRequirement(damage: Int) extends Requirement
case class ItemRequirement(item: Item) extends Requirement
case class MinWeightLiftRequirement(weight: Double)  extends Requirement
case class NotBeHungry(competition: Competition)  extends Requirement
