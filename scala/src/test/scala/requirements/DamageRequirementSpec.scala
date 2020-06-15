package requirements

import competitors.{Stat, Viking}
import org.scalatest.{FreeSpec, Matchers}

class DamageRequirementSpec extends FreeSpec with Matchers{

  "A DamageRequirement" -  {

    val powerfulViking = new Viking(stats = Stat(damage = 150,weight = 30,speed = 10))
    val weakViking = new Viking(stats = Stat(damage = 50,weight = 40,speed = 20))

    "when it requires more than 100 damage" - {
      val damageRequeriment = new DamageRequirement(damageRequired = 100<= )

      "should be true if a viking has more than 100 damage" in {
        assert(damageRequeriment.meetRequirement(powerfulViking))
      }

      "should be false if a viking has less than 100 damage" in {
        assert(!damageRequeriment.meetRequirement(weakViking))
      }
    }

    "when it requires less than 100 damage" - {
      val damageRequeriment = new DamageRequirement(damageRequired = 100>= )

      "should be false if a viking has more than 100 damage" in {
        assert(!damageRequeriment.meetRequirement(powerfulViking))
      }

      "should be true if a viking has less than 100 damage" in {
        assert(damageRequeriment.meetRequirement(weakViking))
      }
    }

    "when it requires 150 damage" - {
      val damageRequeriment = new DamageRequirement(damageRequired = 150 ==)

      "should be true if a viking has 100 damage" in {
        assert(damageRequeriment.meetRequirement(powerfulViking))
      }

      "should be false if a viking doesn't have 100 damage" in {
        assert(!damageRequeriment.meetRequirement(weakViking))
      }
    }
  }
}
