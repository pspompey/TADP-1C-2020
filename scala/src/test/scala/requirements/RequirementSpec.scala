package requirements

import competitors.{Stat, Viking}
import items.{Edible, FlySystem, Weapon}
import org.scalatest.{FreeSpec, Matchers}

class RequirementSpec extends FreeSpec with Matchers {


  "A MaxWeightRequirement" -  {
    val requirement = MaxWeightRequirement(50)
    val heavyViking = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10))
    val lightViking = new Viking(stats = Stat(damage = 50,weight = 40,speed = 10))

    "when it requires a max weight of 50" - {
      "should return true if the viking weighs less" in {
        assert(requirement(lightViking))
      }
      "should return false if the viking weighs more" in {
        assert(!requirement(heavyViking))
      }
    }

  }

  "A MaxDamageRequirement" -  {
    val requirement = MaxDamageRequirement(50)
    val strongViking = new Viking(stats = Stat(damage = 60,weight = 60,speed = 10))
    val weakViking = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10))

    "when it requires a max damage of 50" - {
      "should return true if the viking has less damage" in {
        assert(requirement(weakViking))
      }
      "should return false if the viking has more damage" in {
        assert(!requirement(strongViking))
      }
    }

  }

  "A MinDamageRequirement" -  {
    val requirement = MinDamageRequirement(50)
    val strongViking = new Viking(stats = Stat(damage = 60,weight = 60,speed = 10))
    val weakViking = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10))

    "when it requires a min damage of 50" - {
      "should return false if the viking has less damage" in {
        assert(!requirement(weakViking))
      }
      "should return true if the viking has more damage" in {
        assert(requirement(strongViking))
      }
    }

  }

  "A MinCapacityRequirement" -  {
    val requirement = MinCapacityRequirement(50)
    val strongViking = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10)) //  capacity = 130
    val weakViking = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10)) // capacity = 40

    "when it requires a min capacity of 50" - {
      "should return false if the viking has less damage" in {
        assert(!requirement(weakViking))
      }
      "should return true if the viking has more damage" in {
        assert(requirement(strongViking))
      }
    }

  }

  "An ItemRequirement" -  {
    "when it requires an item" - {
      val requirement = ItemRequirement(FlySystem)
      val vikingWithItem = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10),item = Some(FlySystem))
      val vikingWithoutItem = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10))

      "should return true if the viking has it" in {
        assert(requirement(vikingWithItem))
      }
      "should return false if the viking doesn't have it" in {
        assert(!requirement(vikingWithoutItem))
      }
    }

    "when it requires an weapon" - {
      val requirement = ItemRequirement(Weapon(10))
      val vikingWithItem = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10),item = Some(Weapon(20)))
      val vikingWithoutItem = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10),item = Some(FlySystem))

      "should return true if the viking has it" in {
        assert(requirement(vikingWithItem))
      }
      "should return false if the viking doesn't have it" in {
        assert(!requirement(vikingWithoutItem))
      }
    }

    "when it requires an edible" - {
      val requirement = ItemRequirement(Edible())
      val vikingWithItem = new Viking(stats = Stat(damage = 50,weight = 60,speed = 10),item = Some(Edible()))
      val vikingWithoutItem = new Viking(stats = Stat(damage = 10,weight = 40,speed = 10),item = Some(Weapon(0)))

      "should return true if the viking has it" in {
        assert(requirement(vikingWithItem))
      }
      "should return false if the viking doesn't have it" in {
        assert(!requirement(vikingWithoutItem))
      }
    }


  }
}
