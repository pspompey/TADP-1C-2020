package Competidor


case class Vikingo(stats: Stats, item: Item) {
  val peso: Double = stats.peso
  val velocidad: Int = stats.velocidad
  val barbarosidad: Int = stats.barbarosidad
  val nivelHambre: Int = stats.nivelHambre

  lazy val danio: Int = {
    item match {
      case Hacha => barbarosidad + 30
      case Mazo => barbarosidad + 100
      case _ => barbarosidad
    }
  }

  def aumentarHambre (cantidad: Int): Vikingo = copy (stats = stats.aumentarHambre (cantidad) )

}

object Astrid extends Vikingo(Stats(60,3,5), Hacha){}

trait Item

case object SistemaVuelo extends Item

case object Hacha extends Item

case object Mazo extends Item

case object Comestible extends Item