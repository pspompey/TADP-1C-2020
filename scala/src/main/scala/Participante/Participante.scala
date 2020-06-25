package Participante

import Dragon.Dragon
import Postas.Posta

import scala.util.{Failure, Success, Try}

sealed trait Participante {
  val peso: Double
  val velocidad: Double
  val pesoTolerado: Double
  val danio: Double
  val barbarosidad: Int
  val item: Option[Item]
  val nivelHambre: Int

  def aumentarHambre(porcentaje: Int): Vikingo
}

case class Vikingo(stats: Stats, item: Option[Item] = None) extends Participante {
  val peso: Double = stats.peso
  override val nivelHambre: Int = stats.nivelHambre
  override val barbarosidad: Int = stats.barbarosidad
  override val velocidad: Double = stats.velocidad

  override val danio: Double = {
    item match {
      case Some(Hacha) => barbarosidad + 30
      case Some(Mazo) => barbarosidad + 100
      case _ => barbarosidad
    }
  }

  override val pesoTolerado: Double = peso / 2 + barbarosidad * 2


  override def aumentarHambre(porcentaje: Int): Vikingo = copy(stats = stats.aumentarHambre(porcentaje))


  def intentarMontarDragon(dragon: Dragon): Try[Jinete] = {
    if (dragon.admiteVikingo(this)) Success(Jinete(this, dragon))
    else Failure(NoAdmiteVikingoException())
  }

  def esMejorQue(otroVikingo: Vikingo)(posta: Posta): Boolean = {
    posta(List(this,otroVikingo)).head == this
  }

//  def mejorMontura(dragones: List[Dragon])(posta: Posta): Participante = dragones.map(intentarMontarDragon)

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
  override val barbarosidad: Int = vikingo.barbarosidad
  override val item: Option[Item] = vikingo.item
  override val nivelHambre: Int = vikingo.nivelHambre

  override def aumentarHambre(porcentaje: Int): Vikingo = vikingo.copy(stats = vikingo.stats.aumentarHambre(porcentaje))

}