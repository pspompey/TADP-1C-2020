package competitions

import competitors._
import dragons.Dragon

case class Tournament(competitions: List[Competition], dragons: List[Dragon], rule: Rule) extends (List[Viking] => Option[Viking]){

  def apply(vikings: List[Viking]): Option[Viking] = {
    competitions.foldLeft(vikings)((competitors, competition) => {
      if (competitors.length == 1)
        return competitors.headOption
      else
        rule(competition(prepare(competition, competitors)))
    }).headOption
  }

  def prepare(competition: Competition, competitors: List[Viking]): List[Competitor] = {
    competitors.flatMap(v => v.bestMount(dragons, competition))
  }

}