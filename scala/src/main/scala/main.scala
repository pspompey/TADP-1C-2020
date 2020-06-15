import dragons.DeadlyNadder

object ScalaApp {

  def main(args: Array[String]) {
    val deadlyNadder = new DeadlyNadder(40)

    println(deadlyNadder.damage)
  }

}
