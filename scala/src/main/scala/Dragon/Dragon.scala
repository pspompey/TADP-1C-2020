package Dragon

import Competidor.{Item, SistemaVuelo, Vikingo}

sealed trait Dragon {
  val velocidadBase = 60
  val velocidad = velocidadBase - peso
  val peso: Int
  val danio: Int
  val restriccionesBase: List[Restriccion] = List(RestriccionBasePeso(pesoTolerado))
  val restricciones: List[Restriccion]
  lazy val pesoTolerado: Double = peso * 0.2
  val disponible = true


//TODO : ver tema DISPONIBILIDAD para no modificar estado de un Dragon y generar un nuevo Dragon
  // Necesitamos un nuevo dragon?
  // def cambiarDisponibilidad(): Dragon
  def todasRestricciones: List[Restriccion] = List.concat(restricciones, restriccionesBase)
  def admiteVikingo(vikingo: Vikingo): Boolean = disponible && todasRestricciones.forall(restric => restric(vikingo))



}


case class FuriaNocturna(danio: Int, peso: Int, restricciones: List[Restriccion] = List[Restriccion]()) extends Dragon {
  override val velocidad: Int = velocidad * 3


}
case class NadderMortifero(peso:Int, restricciones: List[Restriccion] = List[Restriccion]()) extends  Dragon{
  val danio = 150
}

case class Gronkle(peso: Int, restricciones: List[Restriccion] = List[Restriccion]()) extends Dragon{
  val danio = 5 * peso
  override val velocidadBase: Int = super.velocidadBase / 2
}

trait Restriccion extends (Vikingo => Boolean)

case class SuperaBarbarosidadMinima(minimo: Int) extends Restriccion {
  override def apply(vikingo: Vikingo): Boolean = vikingo.barbarosidad > minimo
}

case class TieneItem(item: Item) extends Restriccion {
  override def apply(vikingo: Vikingo): Boolean = vikingo.item == item
}


case class DañoVikingoSuperaDaño(danio: Int) extends Restriccion {
  override def apply(vikingo: Vikingo): Boolean = vikingo.danio > danio
}

case class SuperaPeso(peso: Int) extends Restriccion {
  override def apply(vikingo: Vikingo): Boolean = vikingo.peso > peso
}

case class RestriccionBasePeso(pesoTolerado: Double) extends Restriccion {
  override def apply(vikingo: Vikingo): Boolean = pesoTolerado > vikingo.peso
}

