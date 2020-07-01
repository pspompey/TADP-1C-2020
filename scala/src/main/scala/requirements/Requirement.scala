package requirements

import competitions.Competition
import competitors.Competitor
import items.Item

trait Requirement extends (Competitor => Boolean)

case class MaxWeightRequirement(weight: Double) extends Requirement{
  override def apply(competitor: Competitor): Boolean = weight >= competitor.weight
}

case class MinCapacityRequirement(capacity: Double) extends Requirement{
  override def apply(competitor: Competitor): Boolean = capacity <= competitor.capacity
}

case class MinDamageRequirement(damage: Int) extends Requirement{
  override def apply(competitor: Competitor): Boolean = damage <= competitor.damage
}

case class MaxDamageRequirement(damage: Int) extends Requirement{
  override def apply(competitor: Competitor): Boolean = damage >= competitor.damage
}
case class ItemRequirement(item: Item) extends Requirement{
  override def apply(competitor: Competitor): Boolean = competitor.hasItem(item)
}
case class MinWeightLiftRequirement(weight: Double)  extends Requirement{
  override def apply(competitor: Competitor): Boolean = weight <= competitor.weight
}
case class NotBeHungry(competition: Competition)  extends Requirement{
  override def apply(competitor: Competitor): Boolean = competitor.NotBeHungry(competition)
}
