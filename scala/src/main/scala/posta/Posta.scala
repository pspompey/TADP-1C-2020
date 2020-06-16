package posta

import competitors.Viking

sealed trait Posta extends (List[Viking] => List[Viking])


case object Fishing extends Posta{
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(_.capacity)(Ordering[Double].reverse)
  }
}


case class Fight() extends Posta {
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(viking => viking.damage)
  }
}


case class Race(kms: Int) extends Posta {
  override def apply(vikings: List[Viking]): List[Viking] = {
    vikings.sortBy(viking => viking.speed)
  }
}


