package requirements

import org.scalatest.{FreeSpec, Matchers}
import competitors.{Stat, Viking}

class WeightRequirementSpec extends FreeSpec with Matchers{

  "A WeightRequirement" -  {

    val heavyViking = new Viking(stats = Stat(damage = 150,weight = 60,speed = 10))
    val lightViking = new Viking(stats = Stat(damage = 50,weight = 30,speed = 20))

    "when it requires more than 50 weight" - {
      val weightRequirement = WeightRequirement(weightRequired = 50<= )

      "should be true if a viking weighs more than 50" in {
        assert(weightRequirement.meetRequirement(heavyViking))
      }

      "should be false if a viking weighs less than 50" in {
        assert(!weightRequirement.meetRequirement(lightViking))
      }
    }

    "when it requires less than 50 weight" - {
      val weightRequirement = WeightRequirement(weightRequired = 50>= )

      "should be false if a viking weighs more than 50" in {
        assert(!weightRequirement.meetRequirement(heavyViking))
      }

      "should be true if a viking weighs less than 50" in {
        assert(weightRequirement.meetRequirement(lightViking))
      }
    }

    "when it requires 60 weight" - {
      val weightRequirement = WeightRequirement(weightRequired = 60 ==)

      "should be true if a viking weighs 60" in {
        assert(weightRequirement.meetRequirement(heavyViking))
      }

      "should be true if a viking doesn't weighs 60" in {
        assert(!weightRequirement.meetRequirement(lightViking))
      }
    }
  }
}
