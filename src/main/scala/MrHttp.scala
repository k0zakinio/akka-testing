import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

import scala.io.StdIn

object MrHttp {

  def main(args: Array[String]) = {

    implicit val system = ActorSystem("my-system")
    implicit val masterializer = ActorMaterializer()

    implicit val ex = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Hey back from Akka"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println("Server online at localhost:8080, press RETURN to stop")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}
