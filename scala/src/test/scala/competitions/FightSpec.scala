package competitions

import competitors.{Astrid, Patan, Patapez, Stat, Viking}
import items.WeaponType
import org.scalatest.{FreeSpec, Matchers}
import requirements.{ItemRequirement, Requirement}

class FightSpec extends FreeSpec with Matchers{

  "A Fight" -  {

    val vikings = List(Patapez,Patan,Astrid)
    val competition = new Fight()

    "when it is called with a list of vikings" - {
      val result = competition(vikings)

      "should return the vikings ordered by their damage" in {
        val ordering = List(Patan.compete(competition),Astrid.compete(competition),Patapez.compete(competition))
        assertResult(ordering)(result)
      }
    }

    "when it requires a weapon" - {
      val requirement = ItemRequirement(WeaponType)
      val competition = Fight(List(requirement))

      val result = competition(vikings)

      "should return the vikings who has weapons ordered by their damage" in {
        val ordering = List(Patan.compete(competition),Astrid.compete(competition))
        assertResult(ordering)(result)
      }
    }

  }
}
