package characters

import competitors.{Astrid, Hipo, Patan}
import dragons.NightFury
import items.FlySystemType
import org.scalatest.{FreeSpec, Matchers}
import requirements.ItemRequirement

class NightFurySpec extends FreeSpec with Matchers{

  "A NightFury" -{

    "when it is instantiated with 100 damage, 150 weight"-{
      val chimuelo = new NightFury(100,150)

      "should have 100 damage, 30 speed, 150 weight and 30 capacity" in {
        assert(chimuelo.damage == 100)
        assert(chimuelo.speed == 30)
        assert(chimuelo.weight == 150)
        assert(chimuelo.capacity == 30)
      }

      "when a viking tries to ride it" -{
        "could do it if he weighs less than the dragon capacity" in {
          assert(chimuelo.canRide(Astrid))
        }
        "could do it if he weighs more than the dragon capacity" in {
          assert(!chimuelo.canRide(Patan))
        }
      }
    }

    "when it requires a FlySystem" -{
      val chimuelo = new NightFury(100,150,List(ItemRequirement(FlySystemType)))

      "could do it if he weighs less than the maximun capacity" in {
        assert(chimuelo.canRide(Hipo))
      }
      "couldn't do it if he weighs more than the maximun capacity" in {
        assert(!chimuelo.canRide(Astrid))
      }
    }
  }
}
