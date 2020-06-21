package competitions

import competitors.Viking

trait Rule extends (List[Viking] => List[Viking]){
  def apply(vikings: List[Viking]): List[Viking] = this match {
    case Standard => vikings.take((vikings.length.toDouble/2).ceil.toInt)
    case _ => vikings
  }
}

object Standard extends Rule
