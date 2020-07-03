package characters

import competitions.{Fight, Fishing}
import competitors.{Stat, Viking}
import dragons.{DeadlyNadder, Gronckle, NightFury}
import items.Weapon
import org.scalatest.{FreeSpec, Matchers}

class VikingSpec extends FreeSpec with Matchers {

  "A viking and dragons" -  {

    val viking = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10))
    val deadlyNadder = new DeadlyNadder(weight = 1000)
    val nightFury = new NightFury(damage = 100, weight = 200)
    val gronckle = new Gronckle(weight = 100, maxCapacity = 60)
    val dragons = List(deadlyNadder, nightFury, gronckle)
    val competition = new Fishing()

    "Asking for the best mount" - {
      "should return the rider viking and deadlyNadder" in {
        assertResult(viking.bestMount(dragons, competition))(viking.ride(deadlyNadder.copy()))
      }
    }

  }

  "Two Vikings" -  {

    val viking1 = Viking(stats = Stat(damage = 30,weight = 70,speed = 10), hunger = 0, item = None)
    val viking2 = Viking(stats = Stat(damage = 30,weight = 70,speed = 10), hunger = 0, item = Some(Weapon(1000)))
    val competition = new Fight()

    "Asking which one is better" - {
      "should return the viking2 is better than viking1" in {
        assert(viking2.isBetter(viking1, competition))
      }
    }

  }
}
