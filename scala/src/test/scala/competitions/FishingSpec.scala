package competitions

import competitors.{Stat, Viking}
import org.scalatest.{FreeSpec, Matchers}

class FishingSpec extends FreeSpec with Matchers{

  "A Fishing" -  {

    val viking1 = Viking(stats = Stat(damage = 50,weight = 60,speed = 10), hunger = 100, item = None) // capacity = 130
    val viking2 = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10)) // capacity = 95
    val viking3 = new Viking(stats = Stat(damage = 40,weight = 40,speed = 10)) // capacity = 100
    val vikings = List(viking3,viking2,viking1)
    val competition = new Fishing()

    "when it is called with a list of vikings" - {
      "should return the vikings ordered by their capacity" in {
        val result = competition(vikings)
        val ordering = List(viking3.compete(competition),viking2.compete(competition))

        assertResult(ordering)(result)
      }
    }

  }
}
