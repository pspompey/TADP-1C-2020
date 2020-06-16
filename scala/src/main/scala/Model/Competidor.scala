package Model

import scala.util.{Failure, Success, Try}


//case clase por inmutabilidad
trait Competidor

case class Vikingo(atributos: Atributos, hambre: Int, items: List[Item]) extends Competidor{

  def cumpleRequisito(requisito: Restriccion): Boolean = requisito match{
    case RestriccionPeso(pesoTolerado) => peso < pesoTolerado
    case RestriccionBarbarosidad(barbarosidad) => atributos.barbarosidad < barbarosidad
    case RestriccionItem(item) => items.contains(item)
    case RestriccionDanio(danio) => atributos.danio < danio
  }

  def peso: Double = atributos.peso

  def montar(dragon: Dragon): Try[Jinete] =
  {
    if(!dragon.puedeMontar(this))
      Failure(NoPuedeMontarElDragon)
    else
      Success(Jinete(this.copy(), dragon))
  }

}
case class Jinete(vikingo: Vikingo, dragon: Dragon) extends Competidor{
  def peso: Double = vikingo.peso + dragon.peso
}

trait Item

case class Atributos(peso: Double, velocidad: Double, barbarosidad: Int, danio: Double)

