package competitions

import competitors.{Astrid, Hipo, Patan, Patapez}
import dragons.DeadlyNadder
import org.scalatest.{FreeSpec, Matchers}
import requirements.MountRequirement

class RaceSpec extends FreeSpec with Matchers{

  "A Race of 15 kms" -  {

    val vikings = List(Hipo,Patapez,Patan,Astrid)
    val competition = new Race(15)

    "when it is called with a list of vikings" - {
      val result = competition(vikings)

      "should return the vikings ordered by their speed" in {
        val ordering = List(Hipo.compete(competition),Astrid.compete(competition),Patan.compete(competition),Patapez.compete(competition))
        assertResult(ordering)(result)
      }
    }
  }

  "A Race" - {
    "when it requires a mount" - {

      val dragon = new DeadlyNadder(120)
      val dragon2 = new DeadlyNadder(120)
      val competitors = List(Hipo,Patapez,Patan,Astrid.ride(dragon).get)
      val requirement = MountRequirement
      val competition = Race(10,List(requirement))

      val result = competition(competitors)

      "should return the riders ordered by their speed" in {
        val ordering = List(Astrid.ride(dragon2).map(r => r.compete(competition)).getOrElse(None))
        assertResult(ordering)(result)
      }
    }
  }
}
