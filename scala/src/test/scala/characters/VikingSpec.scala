package characters

import competitions.{Fight, Fishing, Race}
import competitors.{Astrid, Hipo, Patan, Patapez, Stat, Viking}
import dragons.{DeadlyNadder, Gronckle, NightFury}
import items.Weapon
import org.scalatest.{FreeSpec, Matchers}

class VikingSpec extends FreeSpec with Matchers {

  "A viking" - {
    "when he competes" - {
      "should have 10 hunger if he competes in a fight" in {
        assert(Hipo.compete(new Fight()).hunger == 10)
      }
      "should have 5 hunger if he competes in a fishing" in {
        assert(Astrid.compete(new Fishing()).hunger == 5)
      }
      "should have 3 hunger if he competes in a race of 3 kms" in {
        assert(Patan.compete(new Race(3)).hunger == 3)
      }
      "shouldn't compete if he overcome 100% hunger" in {
        val competition = new Race(150)
        val result = competition(List(Hipo))
        assertResult(List())(result)
      }
    }

    "when asking the damage" - {
      "should return the damage plus the item damage if he has a weapon" in {
        assert(Astrid.damage == 80)
      }
      "should return only the damage if he doesn't have a weapon" in {
        assert(Hipo.damage == 50)
      }
    }

    "when asking the capacity" - {
      "should return half of the weight plus double of the damage" in {
        assert(Patan.capacity == 120)
      }
    }
  }

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
    "Asking which one is better in a fight" - {
      val competition = new Fight()
      "should return the viking2 is better than viking1" in {
        assert(Patan.isBetter(Hipo, competition))
      }
    }

  }


  "Patapez" - {
    "when he finish a competition" -{
      "should have double of hunger than the competition effect and use the edible to reduce the effect of hunger a 10%" in {
        assert(Patapez.compete(new Race(15)).hunger == 20.0)
      }
      "shouln't have less than 0% hunger" in {
        assert(Patapez.compete(new Race(1)).hunger >= 0.0)
      }
      "shouldn't compete if he overcome 50% hunger" in {
        val competition = new Race(35)
        val result = competition(List(Patapez))
        assertResult(List())(result)
      }
    }
  }
}
