package competitions

import competitors.{Stat, Viking}
import org.scalatest.{FreeSpec, Matchers}
import requirements.Requirement

class FightSpec extends FreeSpec with Matchers{

  "A Fight" -  {

    val viking1 = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10))
    val viking2 = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10))
    val viking3 = new Viking(stats = Stat(damage = 40,weight = 40,speed = 10))
    val vikings = List(viking3.copy(),viking2.copy(),viking1.copy())
    val competition = new Fight()

    "when it is called with a list of vikings" - {
      "should return the vikings ordered by their damage" in {
        val result = competition.apply(vikings)
        val ordering = List(viking1.compete(competition),viking3.compete(competition),viking2.compete(competition))

        assertResult(ordering)(result)
      }
    }

  }
}
