package competitions

import competitors.Viking
import dragons.Dragon
import requirements.Requirement

trait Rule extends ((Competition, List[Dragon], List[Viking]) => List[Viking]){
  def apply(competition: Competition, dragons: List[Dragon], vikings: List[Viking]): List[Viking] =
    this match {
    case Standard => compete(competition, vikings, dragons).take(halfList(vikings))
    case Elimination(n) => compete(competition, vikings, dragons).dropRight(n)
    case Inverse => compete(competition, vikings, dragons).drop(halfList(vikings))
    case BannedDragon(requirement) => compete(competition, vikings, dragons.filter(requirement)).take(halfList(vikings))
    case Handicap => compete(competition, vikings.reverse, dragons).take(halfList(vikings))
    case _ => vikings
  }

  def halfList(list: List[_]): Int = (list.length.toDouble/2).ceil.toInt

  def compete(competition: Competition, vikings: List[Viking], dragons: List[Dragon]): List[Viking] = {
    competition(vikings.flatMap(v => v.bestMount(dragons, competition)))
  }
}

object Standard extends Rule
case class Elimination(n: Int) extends Rule
object Inverse extends Rule
case class BannedDragon(requirement: Dragon => Boolean) extends Rule
object Handicap extends Rule
