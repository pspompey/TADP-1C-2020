package requirements

import items.Item
import characters.competitors.Viking

class ItemRequirement(itemRequired: String => Boolean) extends Requirement {

  override def require(viking: Viking): Boolean = itemRequired(viking.item)
}
