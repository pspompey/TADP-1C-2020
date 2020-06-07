package requirements

import characters.competitors.Viking

trait Requirement {

  def require(viking: Viking):Boolean
}
