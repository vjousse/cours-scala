import akka.actor.{ ActorRef, ActorSystem, ActorLogging, Props, Actor, Inbox }
import scala.concurrent.duration._

case class Greeting(who: String)

class GreetingActor extends Actor with ActorLogging {
  def receive = {
    case Greeting(who) ⇒ log.info("Hello " + who)
  }
}

object TestActor extends App {
  val system = ActorSystem("MySystem")
  val greeter = system.actorOf(Props[GreetingActor], name = "greeter")
  greeter ! Greeting("Charlie Parker")
}
