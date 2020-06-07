package requirements
import characters.competitors.Viking

class DamageRequeriment(damageRequired: Int => Boolean) extends Requirement {

  override def require(viking: Viking): Boolean = damageRequired(viking.damage)
}
