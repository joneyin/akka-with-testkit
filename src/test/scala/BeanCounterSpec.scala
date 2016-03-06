import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.duration._

class BeanCounterSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with Matchers
  with FlatSpecLike
  with BeforeAndAfterAll {

    def this() = this(ActorSystem("BeanCounterSpec"))

    override def afterAll: Unit = {
      system.shutdown()
      system.awaitTermination(10.seconds)
    }

    "A BeanCounterActor" should "send Count(0) when bean count is 0" in {
      val beanCounterActor = system.actorOf(Props[BeanCounterActor])
      beanCounterActor ! GetCount("beans")
      expectMsgType[Long] should be(0)
    }

    "A BeanCounterActor" should "send n when bean count is n" in {
      val n = 4
      val beanCounterActor = TestActorRef[BeanCounterActor]
      val uActor = beanCounterActor.underlyingActor
      uActor.count = n
      beanCounterActor ! GetCount("beans")
      expectMsgType[Long] should be(n)
    }

    "2nd Testing Method: NOT USING TestActorRef A BeanCounterActor" should "send value of n when bean count is n" in {

      val beanCounterActor = system.actorOf(Props[BeanCounterActor])
      beanCounterActor ! SetCount(4)
      beanCounterActor ! GetCount("beans")
      expectMsgType[Long] should be(4)
    }

    "A BeanCounterActor" should "send a message saying we do not count tomatoes when asked to count tomatoes" in {
      val beanCounterActor = system.actorOf(Props[BeanCounterActor])
      beanCounterActor ! GetCount("tomatoes")
      expectMsgType[WeDoNotCount].message should be ("Can't count, won't count tomatoes. i only count beans")
    }

    "A BeanCounterActor" should "send a message reporting an error if a unexpected message is received" in {
      val beanCounterActor = system.actorOf(Props[BeanCounterActor])
      beanCounterActor ! "A STRANGE MESSAGE"
      expectMsg("Error")
    }



}