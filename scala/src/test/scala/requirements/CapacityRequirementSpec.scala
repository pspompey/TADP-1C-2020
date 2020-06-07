package requirements

import org.scalatest.{FreeSpec, Matchers}
import characters.Stat
import characters.competitors.Viking

class CapacityRequirementSpec extends FreeSpec with Matchers{

  "A CapacityRequirement" -  {

    val viking = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10))

    "when it requires more than 100" - {
      val capacityRequirement = new CapacityRequirement(capacityRequired = 100<= )

      "should be true if a viking has more than 100 capacity" in {
        assert(capacityRequirement.require(viking))
      }
    }

    "when it requires less than 100" - {
      val capacityRequirement = new CapacityRequirement(capacityRequired = 100>= )

      "should be false if a viking has more than 100 capacity" in {
        assert(!capacityRequirement.require(viking))
      }
    }

    "when it requires 130" - {
      val capacityRequirement = new CapacityRequirement(capacityRequired = 130== )

      "should be true if a viking has 130 capacity" in {
        assert(capacityRequirement.require(viking))
      }
    }

  }
}
