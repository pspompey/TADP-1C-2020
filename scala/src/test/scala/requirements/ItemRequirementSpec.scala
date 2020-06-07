package requirements

import org.scalatest.{FreeSpec, Matchers}
import items.Item
import characters.Stat
import characters.competitors.Viking

class ItemRequirementSpec extends FreeSpec with Matchers{

  "An ItemRequirement" -  {

    val vikingWithWeapon = new Viking(stats = Stat(damage = 150,weight = 60,speed = 10),item = new Item("Weapon") )
    val vikingWithoutWeapon = new Viking(stats = Stat(damage = 150,weight = 60,speed = 10),item = new Item("Armor")  )

    "when it requires a weapon" - {
      val itemRequirement = new ItemRequirement(itemRequired = "Weapon"== )

      "should be true if a viking has a weapon" in {
        assert(itemRequirement.require(vikingWithWeapon))
      }

      "should be false if a viking doesn't have a weapon" in {
        assert(!itemRequirement.require(vikingWithoutWeapon))
      }
    }
  }
}
