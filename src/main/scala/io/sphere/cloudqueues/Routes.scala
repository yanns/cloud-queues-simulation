package io.sphere.cloudqueues

import akka.http.server.Directives._
import akka.http.server.Route
import akka.http.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.ExecutionContext


object Routes {

  def index(implicit ec: ExecutionContext): Route =
    get {
      path("") {
        complete("hello")
      }
    }

  def auth(implicit ec: ExecutionContext): Route =
    path("v2.0" / "tokens") {
      post {
        val ast = Map("access" →
          Map("token" →
            Map("id" → "simulated-token", "expires" → "2212-04-13T22:51:02.000-06:00")))
        complete(ast)
      }
    }

}
