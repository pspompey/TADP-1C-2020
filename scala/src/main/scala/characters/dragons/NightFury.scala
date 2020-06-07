package characters.dragons

import characters.Stat
import requirements.Requirement

class NightFury(damage: Int,weight: Int,requirements:List[Requirement]) extends Dragon(Stat(weight = weight,speed = 60 * 3,damage = damage),requirements) {

  def this(damage: Int,weight: Int) = this(damage,weight,List[Requirement]())
}

