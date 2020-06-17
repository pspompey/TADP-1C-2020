package competitions

import competitors.Viking
import requirements.Requirement

sealed trait Competition extends (List[Viking] => List[Viking])


case object Fishing extends Competition{
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.capacity)(Ordering[Double].reverse)
  }
}

case class Fight(requirements: List[Requirement]) extends Competition {
  val basicEfect: Double = 5.0

  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.filter(viking => requirements.forall(r => viking.meetRequirement(r)))
      .sortBy(_.damage)(Ordering[Int].reverse).map(viking => viking.copy().increaseHungry(this.basicEfect))
  }
}


case class Race(kms: Int) extends Competition {
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.speed)
  }
}


