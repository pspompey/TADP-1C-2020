package requirements
import characters.competitors.Viking

class WeightRequirement(weightRequired: Int => Boolean) extends Requirement {

  override def require(viking: Viking): Boolean = weightRequired(viking.weight)
}
