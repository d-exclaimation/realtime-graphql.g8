//
//  Main.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 5:45 PM.
//

package $package$

import scala.concurrent.duration.DurationInt
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
    Http()
      .newServerAt("localhost", __port__)
      .bind(route)
      .map(_.addToCoordinatedShutdown(10.seconds))
      .onComplete {
        case Failure(exception) =>
          println(s"[info]: Server stopped due to \${exception.getMessage}")
        case Success(_) =>
          println(s"[info]: Server starting at \${__endpoint__}")
      }
  }
}
