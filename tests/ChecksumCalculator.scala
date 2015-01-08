class ChecksumCalculator {

  private var sum = 0

  protected def add(b: Byte): Unit = sum += b
}

class Test(
  val val1: String,
  val2: Int) {

  def testFunction(t: String): Unit =
    println(t)


  def add(v1: Int): Int =
    val2 + v1


}

object Test extends App {

  val testList = List(1, 2, 3, 4, 5, 6)
  println(sum(testList))
  println(sumRecursive(testList))

  def createTest() = {
    val test = new Test("hop", 2)
    println(test.val1)
    println(test.add(3))

    val myList: List[Double] = List(0, 1, 2, 3)
    val myMean: Option[Double] = mean(myList)

    myMean match {
      case Some(value) => println("The mean is " + value)
      case None => println("Mean of an empty list")
    }

    println("The mean is " + myMean.getOrElse(0))

    println(myMean.map(v => "The mean is" + v).getOrElse("Mean of an empty list"))
  }

  def mean(xs: List[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def sum(xs: List[Int]): Int =
    xs.foldLeft(0)((a,b) => a + b)

  def sumPoly[A](xs: List[A], init: A, f: (A,A) => A): A =
    xs.foldLeft(init)((a,b) => f(a, b))

  def sumPoly2[A](xs: List[A], init: A, f: (A,A) => A): A =
    xs.foldLeft(init)(f)

  def sumPolyRecursive[A](xs: List[A], init: A, f: (A,A) => A): A = xs match {
    case x :: xs => f(x, sumPolyRecursive(xs, init, f))
    case Nil => init
  }

  def sumRecursive(xs: List[Int]): Int = xs match {
    case x :: xs => x + sumRecursive(xs)
    case Nil => 0
  }

  def sumPoly[A](xs: List[A], f: List[A] => A): A = f(xs)
}

object SuperHeroes {
  abstract class Character {
    def name: String
  }
  case class Civilian(name: String, wealth: Int) extends Character
  case class SuperHero(name: String, powers: List[String], alterEgo: Option[Civilian]) extends Character
  case class Enemy(name: String, archEnemy: SuperHero) extends Character

  val TonyStark = Civilian("Tony Stark", 1000000)
  val BruceWayne = Civilian("Bruce Wayne", 1000000)
  val ClarkKent = Civilian("Clark Kent", 1000)

  val IronMan = SuperHero("Iron Man", List("SuperhumanStrength", "Genius", "Cyborg"), Some(TonyStark))
  val Batman = SuperHero("Batman", List("Genius", "Gadgets"), Some(BruceWayne))
  val Superman = SuperHero("SuperMan", List("SuperhumanStrength", "Invulnerability"), Some(ClarkKent))
  val Wolverine = SuperHero("Wolverine", List("SuperhumanStrength", "Invulnerability"), None)
}
