package Requerimiento

import Participante._

sealed trait Restriccion[T <: Participante] extends (T => Boolean)

case class SuperaBarbarosidadMinima(minimo: Int) extends Restriccion[Vikingo] {
  override def apply(vikingo: Vikingo): Boolean = vikingo.barbarosidad > minimo
}

case class TieneItem(item: Item) extends Restriccion[Vikingo] {
  override def apply(vikingo: Vikingo): Boolean = vikingo.item.contains(item)
}


case class DanioVikingoSuperaDanio(danio: Double) extends Restriccion[Vikingo] {
  override def apply(vikingo: Vikingo): Boolean = vikingo.danio < danio
}

case class SuperaPeso(peso: Int) extends Restriccion[Vikingo] {
  override def apply(vikingo: Vikingo): Boolean = vikingo.peso > peso
}

case class RestriccionBasePeso(pesoTolerado: Double) extends Restriccion[Vikingo] {
  override def apply(vikingo: Vikingo): Boolean = pesoTolerado > vikingo.peso
}

case class PesoMinimoALevantar(peso: Double) extends Restriccion[Participante] {
  override def apply(participante: Participante): Boolean = participante.pesoTolerado > peso
}

case class BarbarosidadMinimaOArmaEquipada(barbarosidad: Int) extends Restriccion[Participante]{
  override def apply(participante: Participante): Boolean = participante.barbarosidad >= barbarosidad || (participante.item match {
    case Some(Hacha) => true
    case Some(Mazo) => true
    case _ => false
  }) //TODO: ESTA BIEN?
}

case object CumpleConMontura extends Restriccion[Participante]{
  override def apply(participante: Participante): Boolean = participante.isInstanceOf[Jinete]
}



//TODO: CHECK
case object NoPuedeSuperarMaxHambre extends Restriccion[Participante]{
  override def apply(participante: Participante): Boolean = participante.nivelHambre < 100
}
