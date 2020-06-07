package characters

import characters.Stat
import characters.competitors.Viking
import org.scalatest.{FreeSpec, Matchers}
import characters.dragons.DeadlyNadder

class DeadlyNadderSpec extends FreeSpec with Matchers{

  "A DeadlyNadder" -{

    "when it is instantiated with 40 weight"-{
      val deadlyNadder = new DeadlyNadder(40)

      "should have 150 damage, 20 speed, 40 weight and 8 capacity" in {
        assert(deadlyNadder.damage == 150)
        assert(deadlyNadder.speed == 20)
        assert(deadlyNadder.weight == 40)
        assert(deadlyNadder.capacity == 8)
      }

      "when a viking tries to ride it" -{

        val strongViking = new Viking(stats = Stat(damage = 160,weight = 5,speed = 10))
        val weakViking = new Viking(stats = Stat(damage = 50,weight = 5,speed = 20))
        "could do it if he has less damage than the dragon" in {
          assert(deadlyNadder.canRide(weakViking))
        }
        "couldm't do it if he has more damage than the dragon" in {
          assert(!deadlyNadder.canRide(strongViking))
        }

        val heavyViking = new Viking(stats = Stat(damage = 50,weight = 10,speed = 20))
        val lightViking = new Viking(stats = Stat(damage = 50,weight = 5,speed = 20))

        "could do it if he has more weight than the dragon capacity" in {
          assert(deadlyNadder.canRide(lightViking))
        }
        "couldm't do it if he has less weight than the dragon capacity" in {
          assert(!deadlyNadder.canRide(heavyViking))
        }
      }
    }
  }
}
