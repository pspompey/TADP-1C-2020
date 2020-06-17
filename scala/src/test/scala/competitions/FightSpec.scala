package competitions

import competitors.{Stat, Viking}
import org.scalatest.{FreeSpec, Matchers}
import requirements.Requirement

class FightSpec extends FreeSpec with Matchers{

  "A Fight" -  {

    val viking1 = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10)) // capacity = 130
    val viking2 = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10)) // capacity = 95
    val viking3 = new Viking(stats = Stat(damage = 40,weight = 40,speed = 10)) // capacity = 100
    val vikings = List(viking3,viking2,viking1)

    "when it is called with a list of vikings" - {
      "should return the vikings ordered by their damage" in {
        val result = Fight(requirements = List[Requirement]()).apply(vikings)

        result shouldBe 1
      }
    }

  }
}
