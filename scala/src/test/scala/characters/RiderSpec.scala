package characters

import competitors.{Astrid, Patapez}
import dragons.{DeadlyNadder, Gronckle}
import org.scalatest.{FreeSpec, Matchers}

class RiderSpec extends FreeSpec with Matchers{

  "A Rider" -{
    "when a viking ride a dragon"-{
      val deadlyNadder = new DeadlyNadder(150)
      val rider = Astrid.ride(deadlyNadder).get

      "should have the sum of both damages" in {
        assert(rider.damage == 230)
      }
      "should have the speed of the dragon" in {
        assert(rider.speed == -90)
      }
      "should have the difference between the dragon capacity and viking weight as capacity" in {
        assert(rider.capacity == 10)
      }
    }
  }
}
