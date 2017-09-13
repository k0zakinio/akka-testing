import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case object StartMessage
case object PingMessage
case object PongMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint = {
    count += 1
    println("ping")
  }
  override def receive: Receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage => {
      if(count < 99) {
        incrementAndPrint
        sender ! PingMessage
      } else {
        pong ! StopMessage
        println("ping stopped")
        context.stop(self)
      }
    }
  }
}

class Pong extends Actor {
  override def receive: Receive = {
    case PingMessage =>
      println(" pong")
      sender ! PongMessage
    case StopMessage =>
      println(" pong stopped")
      context.stop(self)
  }
}

object Main extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  ping ! StartMessage
}
