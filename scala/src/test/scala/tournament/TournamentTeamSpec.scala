package tournament

import competitions._
import competitors.{Stat, Viking}
import dragons.{DeadlyNadder, Gronckle, NightFury}
import org.scalatest.{FreeSpec, Matchers}
import requirements.{MaxDamageRequirement, Requirement}

class TournamentTeamSpec extends FreeSpec with Matchers{

  "A Tournament Teams" -  {

    val viking1 = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10))
    val viking2 = new Viking(stats = Stat(damage = 60,weight = 70,speed = 20))
    val viking3 = new Viking(stats = Stat(damage = 70,weight = 80,speed = 30))
    val viking4 = new Viking(stats = Stat(damage = 80,weight = 90,speed = 40))
    val viking5 = new Viking(stats = Stat(damage = 90,weight = 100,speed = 50))
    val viking6 = new Viking(stats = Stat(damage = 100,weight = 110,speed = 60))
    val viking7 = new Viking(stats = Stat(damage = 110,weight = 120,speed = 70))
    val viking8 = new Viking(stats = Stat(damage = 120,weight = 130,speed = 80))
    val viking9 = new Viking(stats = Stat(damage = 130,weight = 140,speed = 90))
    val team = List(List(viking3, viking6, viking9), List(viking2, viking5, viking8), List(viking1, viking4, viking7))

    val deadlyNadder = new DeadlyNadder(weight = 1000)
    val nightFury = new NightFury(damage = 100, weight = 200)
    val gronckle = new Gronckle(weight = 100, maxCapacity = 60)
    val dragons = List(deadlyNadder, nightFury, gronckle)

    val competitions = List(new Fight(), new Fishing(), new Race(2))
    val tournament = TournamentTeams(competitions, dragons)

    "when it is called with a list of teams" - {
      "should return the list of vikings they won" in {
        assertResult(Some(List(Viking(Stat(120,130.0,80),17.0,None,1))))(tournament(team))
      }
    }

  }
}
