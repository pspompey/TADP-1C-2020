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
    competitions.foldLeft(defineTeam(teams))((competitors, competition) => {
      val regroup = competitors.flatten.groupBy(_.team).map(_._2)
      if (regroup.filter(_.length > 0).size > 1)
        teamCompete(competitors.flatten, competition)
      else
        return regroup.find(_.length > 0).headOption
    }).headOption
  }

  def teamCompete(competitors: List[Viking], competition: Competition): List[List[Viking]] = {
    Standard(competition, dragons, competitors).groupBy(_.team).map(_._2).toList
  }

  def defineTeam(teams: List[List[Viking]]): List[List[Viking]] = teams.map(list => list.map(_.joinTeam(teams.indexOf(list))))

}