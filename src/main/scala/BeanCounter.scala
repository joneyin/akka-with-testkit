import akka.actor.Actor

//case object Counter
case class GetCount(name: String)
case class SetCount(n: Long)
//I wanted to wrap the no count String in a message type so as to enable more
//options in pattern matching.
case class  WeDoNotCount(message: String)

class BeanCounterActor extends Actor {
  //could have need to wrap this count in a case class, but not needed now.
  var count: Long = 0

  def receive = {

    case GetCount("beans")      => sender ! count

    case GetCount(name)         => sender ! WeDoNotCount("Can't count, won't count %s. i only count beans".format(name))

    //curently used only for for testing, to avoid TestActorRef (alternative method: use testActorRef)
    //possible disadvantage if added only for testing
    case SetCount(n)            => count = n

    case _                      => sender ! "Error"
  }
}
