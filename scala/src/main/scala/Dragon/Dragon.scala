package Dragon

import Participante._
import Requerimiento._

sealed trait Dragon {

  val peso: Double
  val danio: Double
  val restriccionesBase: List[Restriccion[Vikingo]] = List(RestriccionBasePeso(pesoTolerado))
  val restricciones: List[Restriccion[Vikingo]]
  lazy val pesoTolerado: Double = peso * 0.2
//  val disponible = true


//TODO : ver tema DISPONIBILIDAD para no modificar estado de un Dragon y generar un nuevo Dragon
  // Necesitamos un nuevo dragon?
  // def cambiarDisponibilidad(): Dragon

  def velocidadBase: Double = 60  //Lo hicimos como un metodo para poder realizar super, cosa que val no permite...
  def velocidad: Double = velocidadBase - peso

  def todasRestricciones: List[Restriccion[Vikingo]] = List.concat(restricciones, restriccionesBase)
  def admiteVikingo(vikingo: Vikingo): Boolean = todasRestricciones.forall(restric => restric(vikingo)) // && disponible

}


case class FuriaNocturna(danio: Double, peso: Double, restricciones: List[Restriccion[Vikingo]] = List[Restriccion[Vikingo]]()) extends Dragon {
  override def velocidad: Double = super.velocidad * 3
}
case class NadderMortifero(peso:Double, restricciones: List[Restriccion[Vikingo]] = List[Restriccion[Vikingo]]()) extends  Dragon{
  val danio: Double = 150
}

case class Gronckle(peso: Double, restricciones: List[Restriccion[Vikingo]] = List[Restriccion[Vikingo]]()) extends Dragon{
  val danio: Double = 5 * peso
  override def velocidadBase: Double = super.velocidadBase / 2

}

