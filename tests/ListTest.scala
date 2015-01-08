object ListTest extends App {

  def product(ds: List[Double]): Double = ds match {
    case Nil      => 1.0
    case 0.0 :: _ => 0.0
    case x :: xs  => x * product(xs)
  }

}
