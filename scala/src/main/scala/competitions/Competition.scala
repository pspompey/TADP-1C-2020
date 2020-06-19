package competitions

import competitors.{Competitor, Viking}
import requirements.{NotBeHungry, Requirement}

sealed trait Competition extends (List[Competitor] => List[Competitor]){

  val basicRequirement: Requirement = NotBeHungry(this)
  def basicEfect: Double

  def filterMap(competitors: List[Competitor], requirements: List[Requirement]): List[Competitor] = {
    competitors.filter(competitor => (basicRequirement :: requirements).forall(r => competitor.meetRequirement(r)))
      .map(competitor => competitor.compete(this))
  }
}


case class Fishing(requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 5.0

  def this() = this(List[Requirement]())

  override def apply(competitors: List[Competitor]): List[Competitor] = {
    super.filterMap(competitors, requirements).sortBy(_.capacity)(Ordering[Double].reverse)
  }
}

case class Fight(requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 10.0

  def this() = this(List[Requirement]())

  override def apply(competitors: List[Competitor]): List[Competitor] = {
    super.filterMap(competitors, requirements).sortBy(_.damage)(Ordering[Int].reverse)
  }
}


case class Race(kms: Int, requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = kms

  def this(kms: Int) = this(kms, List[Requirement]())

  override def apply(competitors: List[Competitor]): List[Competitor] = {
    super.filterMap(competitors, requirements).sortBy(_.speed)(Ordering[Int].reverse)
  }
}


