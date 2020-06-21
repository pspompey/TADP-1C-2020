package competitions

import competitors._
import dragons.Dragon

case class Tournament(competitions: List[Competition], dragons: List[Dragon], rule: Rule) extends (List[Viking] => Option[Viking]){

  def apply(vikings: List[Viking]): Option[Viking] = {
    competitions.foldLeft(vikings)((competitors, competition) => {
      if (competitors.length > 1)
        rule(competition, dragons, competitors)
      else
        return competitors.headOption
    }).headOption
  }

}