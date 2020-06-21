package items

trait Item{
  val damage: Int = 0
}

case class Weapon(override val damage: Int) extends Item

case object FlySystem extends Item

case class Edible() extends Item
