import akka.actor.{ActorLogging, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.stream.ActorMaterializer
import spray.json._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

case class Message(message: String)

case class Status(status: String)

trait JsonProtocol extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat1(Message)
  implicit val statusFormat = jsonFormat1(Status)
}

class HelloMessage extends JsonProtocol {
  val route: Route =
    path("greet" / Segment) { name: String =>
      val person = Message(s"Hello $name")
      val responseJson = person.toJson.prettyPrint
      complete(
        HttpEntity(
          ContentTypes.`application/json`,
          responseJson
        )
      )
    } ~
      path("health") {
        val status = Status("OK")
        val responseJson = status.toJson.prettyPrint
        complete(
          HttpEntity(
            ContentTypes.`application/json`,
            responseJson
          )
        )
      }
}

object HelloMessage {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("HelloMessage")
    implicit val materializer = ActorMaterializer()

    import system.dispatcher
    val route =  new HelloMessage().route
    Http().bindAndHandle(route, "0.0.0.0", 8080)

  }
}
