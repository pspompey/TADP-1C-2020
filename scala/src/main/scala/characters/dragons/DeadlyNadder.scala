package characters.dragons

import characters.Stat
import requirements.{Requirement, DamageRequeriment}

class DeadlyNadder(weight: Int,_requirements:List[Requirement]) extends Dragon(Stat(weight = weight,speed = 60,damage = 150),_requirements) {

  override val requirements = new DamageRequeriment(damageRequired = this.damage >=) :: _requirements

  def this(weight: Int) = this(weight,List[Requirement]())
}
