package competitions

import competitors.{Stat, Viking}
import items.Weapon
import org.scalatest.{FreeSpec, Matchers}

class VikingIsBetter extends FreeSpec with Matchers{

  "Two Vikings" -  {

    val viking1 = Viking(stats = Stat(damage = 30,weight = 70,speed = 10), hunger = 0, item = None)
    val viking2 = Viking(stats = Stat(damage = 30,weight = 70,speed = 10), hunger = 0, item = Some(Weapon(1000)))
    val competition = new Fight()

    "One is better than the other" - {
      "should return the viking2 is better than viking1" in {
        assert(viking2.isBetter(viking1, competition))
      }
    }

  }
}
