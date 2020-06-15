package competitors

import dragons.Dragon

case class Rider(viking: Viking,dragon: Dragon){

  lazy val stats: Stat = Stat(damage = viking.damage + dragon.damage,weight = dragon.weight + viking.weight,speed = dragon.speed - viking.speed)

  def capacity: Double = dragon.capacity - viking.weight
}
