package io.sphere.cloudqueues

import akka.http.server.Directives._
import akka.http.server.Route

import scala.concurrent.ExecutionContext


object Routes {

  def index(implicit ec: ExecutionContext): Route =
    get {
      path("") {
        complete("hello")
      }
    }

}
