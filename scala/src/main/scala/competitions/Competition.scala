package competitions

import competitors.Viking
import requirements.Requirement

sealed trait Competition extends (List[Viking] => List[Viking]){

  def filter(vikings: List[Viking], requirements: List[Requirement]): List[Viking] = {
    vikings.filter(viking => requirements.forall(r => viking.meetRequirement(r)))
  }
}


case class Fishing(requirements: List[Requirement]) extends Competition {
  val basicEfect: Double = 5.0

  def this() = this(List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filter(vikings, requirements).sortBy(_.capacity)(Ordering[Double].reverse)
      .map(viking => viking.copy().increaseHungry(this.basicEfect))
  }
}

case class Fight(requirements: List[Requirement]) extends Competition {
  val basicEfect: Double = 10.0

  def this() = this(List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filter(vikings, requirements).sortBy(_.damage)(Ordering[Int].reverse)
      .map(viking => viking.copy().increaseHungry(this.basicEfect))
  }
}


case class Race(kms: Int, requirements: List[Requirement]) extends Competition {

  def this(kms: Int) = this(kms, List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filter(vikings, requirements).sortBy(_.speed)(Ordering[Int].reverse)
      .map(viking => viking.copy().increaseHungry(kms))
  }
}


