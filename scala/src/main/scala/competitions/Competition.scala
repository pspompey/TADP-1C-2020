package competitions

import competitors.Viking
import requirements.{NotBeHungry, Requirement}

sealed trait Competition extends (List[Viking] => List[Viking]){

  val basicRequirement: Requirement = NotBeHungry(this)
  def basicEfect: Double

  def filterMap(vikings: List[Viking], requirements: List[Requirement]): List[Viking] = {
    vikings.filter(viking => (basicRequirement :: requirements).forall(r => viking.meetRequirement(r)))
      .map(viking => viking.compete(this))
  }
}


case class Fishing(requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 5.0

  def this() = this(List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filterMap(vikings, requirements).sortBy(_.capacity)(Ordering[Double].reverse)

  }
}

case class Fight(requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 10.0

  def this() = this(List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filterMap(vikings, requirements).sortBy(_.damage)(Ordering[Int].reverse)
  }
}


case class Race(kms: Int, requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = kms

  def this(kms: Int) = this(kms, List[Requirement]())

  override def apply(vikings: List[Viking]): List[Viking] = {
    super.filterMap(vikings, requirements).sortBy(_.speed)(Ordering[Int].reverse)
  }
}


