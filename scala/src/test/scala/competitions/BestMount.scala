package competitions

import competitors.{Stat, Viking}
import dragons._
import org.scalatest.{FreeSpec, Matchers}

class BestMount extends FreeSpec with Matchers{

  "A viking and dragons" -  {

    val viking = new Viking(stats = Stat(damage = 30,weight = 70,speed = 10))
    val deadlyNadder = new DeadlyNadder(weight = 1000)
    val nightFury = new NightFury(damage = 100, weight = 200)
    val gronckle = new Gronckle(weight = 100, maxCapacity = 60)
    val dragons = List(deadlyNadder, nightFury, gronckle)
    val competition = new Fishing()

    "Best mount" - {
      "should return the rider viking and deadlyNadder" in {
        assertResult(viking.bestMount(dragons, competition))(viking.ride(deadlyNadder))
      }
    }

  }
}
