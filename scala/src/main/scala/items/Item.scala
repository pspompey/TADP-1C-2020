package items

trait Item {
  val damage: Int = 0
  val energy: Double = 0
  val itemType: ItemType
}

case class Weapon(override val damage: Int) extends Item{
  override val itemType: ItemType = WeaponType
}

case object FlySystem extends Item {
  override val itemType: ItemType = FlySystemType
}

case class Edible(override val energy: Double) extends Item {
  override val itemType: ItemType = EdibleType
}



trait ItemType

case object WeaponType extends ItemType
case object EdibleType extends ItemType
case object FlySystemType extends ItemType
