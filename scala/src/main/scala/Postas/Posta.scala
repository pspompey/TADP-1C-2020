package Postas

import Participante._
import Requerimiento._

sealed trait Posta extends (List[Participante] => List[Vikingo]) {
  /*
  Prerrequisito: no puede participar en una posta si su nivel de hambre tras participar en la posta alcanzaría el 100%
  Tiene requerimientos cada Posta en particular

  Modificaciones en los participantes, particulares de cada posta (participar)
   */
  val prerrequisito: Restriccion[Participante] = NoPuedeSuperarMaxHambre
  val requerimientoBase: Restriccion[Participante]

  def puedeParticipar(participante: Participante): Boolean = {
    prerrequisito(participante.participar(this)) && requerimientoBase(participante)
  }

  def rankear(participantes: List[Participante]): List[Participante] =
    this match {
      case Pesca(_) => determinarParticipantes(participantes).sortBy(part => part.pesoTolerado).reverse
      case Combate(_) => determinarParticipantes(participantes).sortBy(_.danio).reverse
      case Carrera(_, _) => determinarParticipantes(participantes).sortBy(_.velocidad).reverse
    }

  def determinarParticipantes(participantes: List[Participante]): List[Participante] = participantes.filter(puedeParticipar)

  override def apply(participantes: List[Participante]): List[Vikingo] = rankear(participantes).map(v => v.participar(this))
}

case class Pesca(pesoMinALevantar: Double) extends Posta {
  override val requerimientoBase: Restriccion[Participante] = PesoMinimoALevantar(pesoMinALevantar)


}

case class Combate(barbarosidadMinima: Int) extends Posta {
  override val requerimientoBase: Restriccion[Participante] = BarbarosidadMinimaOArmaEquipada(barbarosidadMinima)


}


case class Carrera(distancia: Int, conMontura: Boolean) extends Posta {
  override val requerimientoBase: Restriccion[Participante] = CumpleConMontura

  override def puedeParticipar(participante: Participante): Boolean = {
    prerrequisito(participante.participar(this)) && (!conMontura || requerimientoBase(participante))
  }

}

//
