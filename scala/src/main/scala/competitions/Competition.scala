package competitions

import competitors.Viking

sealed trait Competition extends (List[Viking] => List[Viking])


case object Fishing extends Competition{
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.capacity)(Ordering[Double].reverse)
  }
}


case class Fight() extends Competition {
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.damage)
  }
}


case class Race(kms: Int) extends Competition {
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.speed)
  }
}


