package Competidor

import Dragon.Dragon

import scala.util.Try


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

  def intentarMontarDragon (dragon: Dragon): Try[Jinete] = ???
}

case object Astrid extends Vikingo(Stats(60,3,5), Hacha){}
case object Hipo extends Vikingo(Stats(70,4,5), SistemaVuelo){}
case object Patan extends Vikingo(Stats(40,2,9), Mazo){}
case object Patapez extends Vikingo(Stats(80,1,5), Comestible){
  ???
}

trait Item

case object SistemaVuelo extends Item

case object Hacha extends Item

case object Mazo extends Item

case object Comestible extends Item


