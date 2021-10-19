//
//  Main.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 5:45 PM.
//

package $package$

import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers.HttpOrigin
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.model.HttpOriginMatcher
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import $package$.services.Counter
import $package$.constants.Environment.{__endpoint__, __port__}
import $package$.implicits.Implicits._
import $package$.schema.{Mutation, Query, Subscription}
import io.github.dexclaimation.ahql.Ahql
import io.github.dexclaimation.overlayer.OverTransportLayer
import io.github.dexclaimation.soda.core.SchemaDefinition.makeSchema

import scala.util.{Failure, Success}

object Main extends SprayJsonSupport {

  val schema = makeSchema(Mutation.t, Query.t, Subscription.t)

  val gqlServer = Ahql.createServer(schema, ())
  val gqlTransport = OverTransportLayer(schema, ())

  val counter = new Counter()

  val corsConfig = CorsSettings
    .defaultSettings
    .withAllowedOrigins(HttpOriginMatcher(HttpOrigin("https://studio.apollographql.com")))
    .withAllowCredentials(true)

  val route: Route = cors(corsConfig) {
    pathPrefix("graphql") {
      pathEndOrSingleSlash {
        gqlServer.applyMiddleware(counter)
      } ~ path("websocket") {
        gqlTransport.applyMiddleware(counter)
      }
    } ~ path(Remaining) { _ =>
      redirect(
        uri = s"http://sandbox.apollo.dev/?endpoint=\${__endpoint__}",
        StatusCodes.PermanentRedirect
      )
    }
  }

  def main(args: Array[String]): Unit = {
    println(s"[akka-http]: Server starting at \${__endpoint__}")
    Http()
      .newServerAt("localhost", __port__)
      .bind(route)
      .onComplete {
        case Failure(exception) => s"[akka-http]: Server stopped due to \${exception.getMessage}"
        case Success(_) => s"[akka-http]: Server finished"
      }
  }
}
