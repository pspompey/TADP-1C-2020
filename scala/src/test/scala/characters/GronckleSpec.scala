package characters

import competitors.{Astrid, Patapez, Stat, Viking}
import dragons.Gronckle
import org.scalatest.{FreeSpec, Matchers}

class GronckleSpec extends FreeSpec with Matchers{

  "A GronckleSpec" -{

    "when it is instantiated with 100 weight and 10 maximun capacity"-{
      val gronckle = new Gronckle(200,30)

      "should have 1000 damage, -170 speed, 200 weight and 40 capacity" in {
        assert(gronckle.damage == 1000)
        assert(gronckle.speed == -170)
        assert(gronckle.weight == 200)
        assert(gronckle.capacity == 40)
      }

      "when a viking tries to ride it" -{
        "could do it if he weighs less than the maximun capacity" in {
          assert(gronckle.canRide(Astrid))
        }
        "couldn't do it if he weighs more than the maximun capacity" in {
          assert(!gronckle.canRide(Patapez))
        }
      }
    }
  }
}
