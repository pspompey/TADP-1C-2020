package items

trait Item{
  val damage: Int = 0
  val energy: Double = 0
}

case class Weapon(override val damage: Int) extends Item

case object FlySystem extends Item

case class Edible(override val energy: Double) extends Item
