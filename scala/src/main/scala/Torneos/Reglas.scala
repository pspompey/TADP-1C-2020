package Torneos

sealed trait Reglas {

}

case class Estandar() extends Reglas
case class Eliminacion(cantidad: Int) extends Reglas
case class Inverso() extends Reglas
case class Veto() extends Reglas
case class Handicap() extends Reglas
case class Equipos() extends Reglas

