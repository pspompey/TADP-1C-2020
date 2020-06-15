package items

sealed trait Item


case class Weapon(damage: Int) extends Item

case object FlySystem extends Item

case class Edible() extends Item