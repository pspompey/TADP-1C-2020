package competitions

import competitors.{Astrid, Hipo, Patan, Patapez, Stat, Viking}
import dragons.{DeadlyNadder, Gronckle}
import org.scalatest.{FreeSpec, Matchers}
import requirements.{MinCapacityRequirement, MountRequirement}

class FishingSpec extends FreeSpec with Matchers{

  "A Fishing" -  {

    val dragon = new Gronckle(400,40)
    val dragon2 = new Gronckle(400,40)
    val competitor = List(Hipo,Patapez,Patan.ride(dragon).get,Astrid)
    val competition = new Fishing()

    "when it is called with a list of competitors" - {
      val result = competition(competitor)

      "should return the vikings ordered by their capacity" in {
        val ordering = List(Patapez.compete(competition)
          ,Hipo.compete(competition)
          ,Astrid.compete(competition)
          ,Patan.ride(dragon2).map(r => r.compete(competition)).getOrElse(None))

        assertResult(ordering)(result)
      }
    }

    "when it requires a minimun capacity" - {
      val competitors = List(Hipo,Patapez,Patan,Astrid)
      val requirement = MinCapacityRequirement(120)
      val competition = Fishing(List(requirement))

      val result = competition(competitors)

      "should return the vikings who overcomes the minimun capacity ordered by their capacity" in {
        val ordering = List(Patapez.compete(competition),Patan.compete(competition))
        assertResult(ordering)(result)
      }
    }
  }
}
