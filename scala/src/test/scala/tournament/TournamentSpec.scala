package tournament

import competitions._
import competitors.{Stat, Viking}
import dragons.{DeadlyNadder, Gronckle, NightFury}
import org.scalatest.{FreeSpec, Matchers}
import requirements.{MaxDamageRequirement, Requirement}

class TournamentSpec extends FreeSpec with Matchers{

  "A Fishing" -  {

    val viking1 = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10)) // capacity = 130
    val viking2 = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10)) // capacity = 95
    val viking3 = new Viking(stats = Stat(damage = 40,weight = 40,speed = 10)) // capacity = 100
    val vikings = List(viking3,viking2,viking1)

    val deadlyNadder = new DeadlyNadder(weight = 1000)
    val nightFury = new NightFury(damage = 100, weight = 200)
    val gronckle = new Gronckle(weight = 100, maxCapacity = 60)
    val dragons = List(deadlyNadder, nightFury, gronckle)

    val competitions = List(new Fight(), new Race(1), Fishing(List[Requirement](MaxDamageRequirement(0))))
    val tournament = Tournament(competitions, dragons, Standard)

    "when it is called with a list of vikings" - {
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(Some(viking3.setHunger(6)))
      }
    }

  }
}
