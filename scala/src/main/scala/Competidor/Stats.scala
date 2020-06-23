package Competidor

case class Stats(peso: Double, velocidad: Int, barbarosidad: Int, nivelHambre: Int = 0) {
  require(nivelHambre <= 100 && nivelHambre >= 0)

  def aumentarHambre(cantidad: Int): Stats = copy(nivelHambre = nivelHambre + cantidad)
}
