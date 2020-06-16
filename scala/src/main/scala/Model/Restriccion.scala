package Model

trait Restriccion extends (Vikingo => Boolean){
  def apply(vikingo: Vikingo): Boolean = vikingo.cumpleRequisito(this)
}

case class RestriccionPeso(peso: Double) extends Restriccion
case class RestriccionDanio(danio: Double) extends Restriccion
case class RestriccionBarbarosidad(barbarosidad: Int) extends Restriccion
case class RestriccionItem(item: Item) extends Restriccion
