package Torneos

import Dragon._
import Participante._
import Postas._

case class Torneo(regla: Reglas ,postas: List[Posta] , vikingos: List[Vikingo], dragones: List[Dragon]) {

   def seleccion(posta: Posta, participantes: List[Vikingo]): List[Participante] = {

    var dragonesRestantes: List[Dragon] = regla match {
      case r: Veto => ???
      case _ => dragones
    }
     def elegirDragon(listaVikingos: List[Vikingo]) : List[Participante] = listaVikingos.map(cadaV =>{
       val participante: Participante = cadaV.mejorMontura(dragonesRestantes)(posta)
       participante match {
         case p: Jinete => dragonesRestantes = dragonesRestantes.filter(cada => cada != p.dragon)
         // case _ => participante
       }
       participante
     })
    regla match {
      case r:Handicap => elegirDragon(participantes.reverse)
      case _ => elegirDragon(participantes)
    }


  }
  def eliminacion(viks: List[Vikingo]): List[Vikingo] = regla match {
    case r: Eliminacion => viks.dropRight(r.cantidad)
    case r: Inverso => viks.reverse.dropRight(viks.length / 2)
    case _ => viks.dropRight(viks.length / 2) // Es el caso Estandar

  }

  def unaPosta(posta: Posta, participantes: List[Vikingo]): List[Vikingo] = eliminacion(posta(seleccion(posta,participantes)))
  def competidoresFinales() : List[Vikingo]= postas.foldLeft(vikingos)((vikingosRestantes,posta) => {
    if (vikingosRestantes.length == 1)  return  vikingosRestantes
    if (vikingosRestantes.isEmpty) return  vikingosRestantes
    unaPosta(posta,vikingosRestantes)
  })

  def ganador(): Option[Vikingo] = { regla match {
    case r: Inverso => competidoresFinales().reverse.headOption
    case _ => competidoresFinales().headOption
  }

  }


    //postas.flatMap(cada => unaPosta(cada))
    // Some(postas.head(seleccion(postas.head)).head)

}
