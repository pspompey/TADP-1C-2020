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

case class TournamentTeams(competitions: List[Competition], dragons: List[Dragon]) extends (List[List[Viking]] => Option[List[Viking]]){

  def apply(teams: List[List[Viking]]): Option[List[Viking]] = {
    val result = competitionResult(teams)
    if(!result.isEmpty)
      Some(teams(result.head.team))
    else
      None
  }

  def competitionResult(teams: List[List[Viking]]): Option[Viking] = {
    competitions.foldLeft(defineTeam(teams).flatten)((competitors, competition) => {
      val regroup = competitors.groupBy(_.team).map(_._2)
      regroup.filter(_.length > 0).size match {
        case 0 => return None
        case 1 => return regroup.find(_.length > 0).head.headOption
        case _ => Standard(competition, dragons, competitors)
      }
    }).headOption
  }

  def defineTeam(teams: List[List[Viking]]): List[List[Viking]] = teams.map(list => list.map(_.joinTeam(teams.indexOf(list))))

}