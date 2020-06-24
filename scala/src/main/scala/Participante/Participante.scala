package Participante

import Dragon.Dragon
import scala.util.{Failure, Success, Try}

sealed trait Participante {
  val peso: Double
  val velocidad: Double
  val pesoTolerado: Double
  val danio: Double

}

case class Vikingo(stats: Stats, item: Item) extends Participante {
  val peso: Double = stats.peso
  val barbarosidad: Int = stats.barbarosidad
  val nivelHambre: Int = stats.nivelHambre
  override val velocidad: Double = stats.velocidad

  override val danio: Double = {
    item match {
      case Hacha => barbarosidad + 30
      case Mazo => barbarosidad + 100
      case _ => barbarosidad
    }
  }

  override val pesoTolerado: Double = peso / 2 + barbarosidad * 2


  def aumentarHambre(cantidad: Int): Vikingo = copy(stats = stats.aumentarHambre(cantidad))

  def intentarMontarDragon(dragon: Dragon): Try[Jinete] = {
    if (dragon.admiteVikingo(this)) Success(Jinete(this, dragon))
    else Failure(NoAdmiteVikingoException())
  }
}

/*
case object Astrid extends Vikingo(Stats(60,3,5), Hacha){}
case object Hipo extends Vikingo(Stats(70,4,5), SistemaVuelo){}
case object Patan extends Vikingo(Stats(40,2,9), Mazo){}
case object Patapez extends Vikingo(Stats(80,1,5), Comestible){
  ???
}
*/
trait Item

case object SistemaVuelo extends Item

case object Hacha extends Item

case object Mazo extends Item

case object Comestible extends Item


case class NoAdmiteVikingoException() extends RuntimeException

case class Jinete(vikingo: Vikingo, dragon: Dragon) extends Participante {
  override val peso: Double = vikingo.peso + dragon.peso
  override val velocidad: Double = dragon.velocidad - 1 * vikingo.peso
  override val danio: Double = vikingo.danio + dragon.danio
  override val pesoTolerado: Double = dragon.pesoTolerado - vikingo.peso


}