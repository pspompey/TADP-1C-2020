package characters.dragons

import characters.Stat
import requirements.{Requirement, WeightRequirement}

class Gronckle(weight: Int,maxCapacity: Int,_requirements:List[Requirement]) extends Dragon(Stat(weight = weight,speed = 60 / 2,damage = 5 * weight),_requirements) {

  override val requirements = new WeightRequirement(weightRequired = this.maxCapacity >=) :: _requirements

  def this(weight: Int,maxCapacity: Int) = this(weight,maxCapacity,List[Requirement]())
}
