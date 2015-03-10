package io.sphere.cloudqueues

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.github.kxbmap.configs._

object Main extends App with Logging {

  implicit val system = ActorSystem()
  import system.dispatcher

  val conf = ConfigFactory.load()
  val httpPort = conf.get[Int]("http.port")

  val routes = Routes.index
  val startedServer = StartedServer("0.0.0.0", httpPort, routes)


  Runtime.getRuntime.addShutdownHook(new Thread {
    override def run(): Unit = {
      println("bye bye")
      startedServer.stop()
    }
  })

  log.info(s"cloud queues simulation started on port $httpPort")
  val lock = new AnyRef
  lock.synchronized { lock.wait() }

}