package competitions

import competitors.{Competitor, Viking}
import requirements.{NotBeHungry, Requirement}

trait Competition extends (List[Competitor] => List[Viking]){

  val basicRequirement: Requirement = NotBeHungry(this)
  val requirements: List[Requirement] = List[Requirement]()

  def basicEfect: Double

  def apply(competitors: List[Competitor]): List[Viking] = {
      simulate(competitors).map(competitor => competitor.compete(this))
  }

  def simulate(competitors: List[Competitor]): List[Competitor] ={
    ordering(competitors.filter(competitor => (basicRequirement :: requirements).forall(r => competitor.meetRequirement(r))))
  }

  def ordering(competitors: List[Competitor]): List[Competitor] = this match {
    case Fishing(_) => competitors.sortBy(_.capacity)(Ordering[Double].reverse)
    case Fight(_) => competitors.sortBy(_.damage)(Ordering[Int].reverse)
    case Race(_, _) => competitors.sortBy(_.speed)(Ordering[Int].reverse)
    case _ => competitors
  }

}


case class Fishing(override val requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 5.0

  def this() = this(List[Requirement]())

}

case class Fight(override val requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = 10.0

  def this() = this(List[Requirement]())

}


case class Race(kms: Int, override val requirements: List[Requirement]) extends Competition {
  override def basicEfect: Double = kms

  def this(kms: Int) = this(kms, List[Requirement]())

}


