package io.sphere.cloudqueues

import akka.http.marshallers.sprayjson.SprayJsonSupport._
import akka.http.model.HttpResponse
import akka.http.model.StatusCodes._
import akka.http.model.headers.Location
import akka.http.server.Directives._
import akka.http.server.Route
import akka.stream.ActorFlowMaterializer
import spray.json.DefaultJsonProtocol._
import spray.json._

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
        val ast =
          JsObject("access" →
            JsObject("token" →
              JsObject(
                "id" → JsString("simulated-token"),
                "expires" → JsString("2212-04-13T22:51:02.000-06:00"))))
        complete(ast)
      }
    }

  case class ClaimRequestBody(ttl: Int, grace: Int)
  object ClaimRequestBody {
    implicit val format = jsonFormat2(ClaimRequestBody.apply)
  }

  def queue(implicit ec: ExecutionContext, materializer: ActorFlowMaterializer): Route = {
    pathPrefix("v1" / "queues") {
      path(Segment) { name ⇒
        put {
          complete(HttpResponse(status = Created).withHeaders(Location(s"/v1/queues/$name")))
        }
      } ~
      path(Segment / "claims") { name ⇒
        parameter('limit.as[Int] ?) { limit ⇒
          post {
            entity(as[ClaimRequestBody]) { claim ⇒

              val ast = JsArray(JsObject(
                "body" → JsObject("event" -> JsString(s"$name $limit")),
                "age" → JsNumber(239),
                "href" → JsString(s"/v1/queues/$name/messages/51db6f78c508f17ddc924357?claim_id=51db7067821e727dc24df754"),
                "ttl" → JsNumber(claim.ttl)))
              complete(Created → ast)
            }
          }
        }
      }
    }
  }
}
