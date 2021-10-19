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
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import $package$.services.Counter
import $package$.constants.Environment.{__endpoint__, __port__}
import $package$.constants.Config
import $package$.implicits.Implicits._
import $package$.schema.{Mutation, Query, Subscription}
import io.github.dexclaimation.ahql.Ahql
import io.github.dexclaimation.overlayer.OverTransportLayer
import io.github.dexclaimation.soda.core.SchemaDefinition.makeSchema

import scala.util.{Failure, Success}

object Main extends SprayJsonSupport {

  /** Compiled schema from Soda */
  val schema = makeSchema(Mutation.t, Query.t, Subscription.t)

  /** GraphQL HTTP Server from Ahql */
  val gqlServer = Ahql.createServer(schema, (),
    httpMethodStrategy = HttpMethodStrategy.queryOnlyGet
  )

  /** GraphQL Websocket Transport from OverLayer */
  val gqlTransport = OverTransportLayer(schema, ())

  /** Singleton context */
  val counter = new Counter()

  /**
   * Akka HTTP Routing
   *
   * ---
   * {{{
   * - POST("__endpoint__/graphql") ~> GraphQL over HTTP (Ahql)
   * - GET("__endpoint__/graphql") ~> GraphQL over HTTP (Ahql, query-only)
   * - WS("__endpoint__/graphql/websocket") ~> GraphQL over Websocket (OverLayer)
   * - GET("__endpoint__/<any-route>") ~> Apollo Sandbox (Redirect)
   * }}}
   */
  val route: Route = cors(Config.cors) {
    pathPrefix("graphql") {
      pathEndOrSingleSlash {
        gqlServer.applyMiddleware(counter)
      } ~ path("websocket") {
        gqlTransport.applyMiddleware(counter)
      }
    } ~ path(Remaining) { _ =>
      redirect(
        uri = s"http://sandbox.apollo.dev/?endpoint=\${__endpoint__}/graphql",
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
