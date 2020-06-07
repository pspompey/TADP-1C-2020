package requirements

import characters.competitors.Viking

class CapacityRequirement(capacityRequired: Double => Boolean) extends Requirement {

  override def require(viking: Viking): Boolean = capacityRequired(viking.capacity)
}
