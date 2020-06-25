package Postas

import Participante._
import Requerimiento._

sealed trait Posta extends (List[Participante] => List[Vikingo]){
  /*
  Prerrequisito: no puede participar en una posta si su nivel de hambre tras participar en la posta alcanzaría el 100%
  Tiene requerimientos cada Posta en particular

  Modificaciones en los participantes, particulares de cada posta (participar)
   */
  val prerrequisito: Restriccion[Participante] = NoPuedeSuperarMaxHambre
  val requerimientoBase: Restriccion[Participante]

  def puedeParticipar(participante: Participante): Boolean
  def participar(participante: Participante): Participante
}

case class Pesca(pesoMinALevantar: Double) extends Posta{
  override val requerimientoBase: Restriccion[Participante] = PesoMinimoALevantar(pesoMinALevantar)

  override def participar(participante: Participante): Vikingo = {
    //Luego de participar en una posta de pesca los vikingos incrementan 5% de su nivel de hambre.
    participante.aumentarHambre(5)
  }

  override def puedeParticipar(participante: Participante): Boolean = {
    prerrequisito(participar(participante)) && requerimientoBase(participante)
  }


  override def apply(participantes:List[Participante]): List[Vikingo] =
    participantes.filter(puedeParticipar).sortBy(_.pesoTolerado).reverse.map(participar)
}