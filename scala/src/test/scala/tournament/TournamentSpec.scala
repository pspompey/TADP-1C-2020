package tournament

import competitions._
import competitors.{Competitor, Stat, Viking}
import dragons.{DeadlyNadder, Gronckle, NightFury}
import org.scalatest.{FreeSpec, Matchers}
import requirements._

class TournamentSpec extends FreeSpec with Matchers {

  "A Fishing Tournament" - {

    val viking1 = new Viking(stats = Stat(damage = 50, weight = 60, speed = 10)) // capacity = 130
    val viking2 = new Viking(stats = Stat(damage = 30, weight = 70, speed = 10)) // capacity = 95
    val viking3 = new Viking(stats = Stat(damage = 40, weight = 40, speed = 10)) // capacity = 100
    val vikings = List(viking3, viking2, viking1)

    val deadlyNadder = new DeadlyNadder(weight = 1000)
    val nightFury = new NightFury(damage = 100, weight = 200)
    val gronckle = new Gronckle(weight = 100, maxCapacity = 60)
    val dragons = List(deadlyNadder, nightFury, gronckle)

    val competitions = List(Fishing(List[Requirement](MinCapacityRequirement(0))))

    "when it is called with a list of vikings with Standard rules" - {
      val tournament = Tournament(competitions, dragons, Standard)
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(Some(viking3.setHunger(5)))
      }
    }

    "when it is called with a list of vikings with Elimination rules" - {
      val tournament = Tournament(competitions, dragons, Elimination(3))
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(None)
      }
    }

    "when it is called with a list of vikings with Inverse rules" - {
      val tournament = Tournament(competitions, dragons, Inverse)
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(Some(viking2.setHunger(5)))
      }
    }

    "when it is called with a list of vikings with BannedDragon rules" - {
      val tournament = Tournament(competitions, dragons, BannedDragon(dragon => dragon.weight > 1000))
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(Some(viking1.setHunger(5)))
      }
    }

    "when it is called with a list of vikings with Handicap rules" - {
      val tournament = Tournament(competitions, dragons, Handicap)
      "should return the vikings ordered by their capacity" in {
        assertResult(tournament(vikings))(Some(viking1.setHunger(5)))
      }
    }

  }
}
