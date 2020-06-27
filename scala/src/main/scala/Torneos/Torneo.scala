package Torneos

import Dragon._
import Participante._
import Postas._

case class Torneo(regla: Reglas ,postas: List[Posta] , vikingos: List[Vikingo], dragones: List[Dragon]) {

   def seleccion(posta: Posta): List[Participante] = {
    var dragonesRestantes: List[Dragon] = dragones
    vikingos.map(cadaV =>{
      val participante: Participante = cadaV.mejorMontura(dragonesRestantes)(posta)
      participante match {
        case p: Jinete => dragonesRestantes = dragonesRestantes.filter(cada => cada != p.dragon)
        case _ => participante
      }
      participante
    }

    )
  }
  def eliminacion(viks: List[Vikingo]): List[Vikingo] = viks.dropRight(viks.length / 2)
  def unaPosta(posta: Posta): List[Vikingo] = eliminacion(posta(seleccion(posta)))
  def ganador() :List[Vikingo] = postas.flatMap(cada => unaPosta(cada))
    // Some(postas.head(seleccion(postas.head)).head)

}
