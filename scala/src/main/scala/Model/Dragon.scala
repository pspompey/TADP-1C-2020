package Model
import scala.util.{Failure, Success, Try}

sealed trait Dragon{
  val velocidadBase: Int = 60
  val restricciones: List[Restriccion] = List[Restriccion](RestriccionPeso(pesoTolerado))

  lazy val pesoTolerado: Double = caracteristicas.peso * 0.2
  lazy val caracteristicas: Caracteristica = Caracteristica(danio = danio, peso = peso, velocidad = velocidadBase)

  def danio: Int = caracteristicas.danio
  def peso: Double = caracteristicas.peso
  def velocidad: Int = caracteristicas.velocidad

  def puedeMontar(vikingo: Vikingo):Boolean = restricciones.forall(r => vikingo.cumpleRequisito(r))

}

case class Caracteristica(danio: Int, peso: Double, velocidad: Int)

case class FuriaNocturna(override val danio:Int, override val peso: Double, listaDeRequerimientos: Option[List[Restriccion]] = None) extends Dragon{

  override lazy val caracteristicas: Caracteristica = Caracteristica(danio = danio, peso = peso, velocidad = velocidadBase * 3)
}

case class NadderMortifero(override val peso: Double, listaDeRequerimientos: Option[List[Restriccion]] = None) extends Dragon{

  override lazy val caracteristicas: Caracteristica = Caracteristica(danio = 150, peso = peso, velocidad = velocidadBase)
}

case class Gronckle (override val peso: Double, pesoMaximoSoportado: Double, listaDeRequerimientos: Option[List[Restriccion]] = None) extends Dragon{

  override lazy val caracteristicas: Caracteristica = Caracteristica(danio = (peso * 5).toInt, peso = peso, velocidad = velocidadBase / 2)
}

object NoPuedeMontarElDragon extends RuntimeException("No cumple los requisitos necesarios")