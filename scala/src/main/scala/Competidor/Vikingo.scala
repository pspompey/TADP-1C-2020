package Competidor


case class Vikingo (_velocidad: Double, _daño: Double, _peso: Double, _barbarosidad: Double, _item: Item)  {
  def barbarosidad = _barbarosidad
  def hambre = 0
  def item = _item
  def velocidad = _velocidad
  def peso = _peso
  def daño = _item match {
        case Hacha => _daño + 30
        case Mazo => _daño + 100
        case _ => _daño
    }

  def aumentarHambre(cantidad: Double): Vikingo ={
    hambre += cantidad
    this
  }
}




trait Item

case object SistemaVuelo extends Item
case object Hacha extends Item
case object Mazo extends Item
case object Comestible extends Item