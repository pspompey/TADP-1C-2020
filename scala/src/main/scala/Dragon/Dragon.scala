package Dragon

abstract class Dragon {
  def velocidadBase = 60
  def velocidad = velocidadBase - peso
  def peso: Double
  def daño: Double

}

case class FuriaNocturna(daño: Double, peso: Double) extends Dragon{
  override def velocidad: Double = velocidad * 3

}
case class NadderMortifero(peso:Double) extends  Dragon{
  def daño = 150

}

case class Gronkle(peso: Double) extends Dragon{
  def daño = 5 * peso

  override def velocidadBase: Int = super.velocidadBase / 2
}
