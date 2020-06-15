package requirements

import competitors.Viking
import items.Item

sealed trait Requirement {

  def meetRequirement(viking: Viking) : Boolean
}


case class WeightRequirement(weightRequired: Double => Boolean) extends Requirement {
  override def meetRequirement(viking: Viking): Boolean = weightRequired(viking.weight)
}


case class DamageRequirement(damageRequired: Int => Boolean) extends Requirement {
  override def meetRequirement(viking: Viking): Boolean = damageRequired(viking.damage)
}


case class CapacityRequirement(capacityRequired: Double => Boolean) extends Requirement {
  override def meetRequirement(viking: Viking): Boolean = capacityRequired(viking.capacity)
}


case class ItemRequirement(itemRequired: Item => Boolean) extends Requirement {
  override def meetRequirement(viking: Viking): Boolean = {
    viking.item match{
      case Some(item) => itemRequired(item)
      case None => false
    }
  }
}