package Dragon

sealed trait Dragon {
  val velocidadBase = 60
  val velocidad = velocidadBase - peso
  val peso: Int
  val daño: Int

}

case class FuriaNocturna(daño: Int, peso: Int) extends Dragon{
  override val velocidad: Int = velocidad * 3

}
case class NadderMortifero(peso:Int) extends  Dragon{
  val daño = 150

}

case class Gronkle(peso: Int) extends Dragon{
  val daño = 5 * peso

  override val velocidadBase: Int = super.velocidadBase / 2
}
