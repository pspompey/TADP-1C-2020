package Participante

case class Stats(peso: Double, velocidad: Double, barbarosidad: Int, nivelHambre: Int = 0) {
  require(nivelHambre <= 100 && nivelHambre >= 0)

  def aumentarHambre(porcentaje: Int): Stats = copy(nivelHambre = nivelHambre + porcentaje)
}
