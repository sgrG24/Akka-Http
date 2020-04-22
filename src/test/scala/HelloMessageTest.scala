import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers, WordSpec}
import akka.http.scaladsl.server.Directives._
import spray.json._

class HelloMessageTest extends FlatSpec with Matchers with ScalatestRouteTest with JsonProtocol {

  val route = new HelloMessage().route

  "Get /health" should "return status on hitting the health endpoint" in {
    Get("/health") ~> route ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Status] shouldBe Status("OK")
    }
  }

  "Get/greet/Sagar" should "return a greet message" in {
    Get("/greet/Sagar") ~> route ~> check {
      status shouldBe StatusCodes.OK
      responseAs[Message] shouldBe Message("Hello Sagar")
    }
  }


}
