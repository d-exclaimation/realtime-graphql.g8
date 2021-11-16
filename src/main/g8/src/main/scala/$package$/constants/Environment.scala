//
//  Environment.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 5:53 PM.
//

package $package$.constants

object Environment {
  /** ENV: PORT */
  val __port__ = sys.env
    .get("PORT")
    .flatMap(_.toIntOption)
    .getOrElse(4000)

  /** ENV: RUNTIME_ENV, for checking if running on production */
  val __prod__ = sys.env
    .get("RUNTIME_ENV")
    .contains("production")

  /** ENV: ENDPOINT, for URI Endpoint for the server */
  val __endpoint__ =
    if (__prod__) sys.env.getOrElse("ENDPOINT", s"http://localhost:${__port__}") 
    else s"http://localhost:${__port__}"
}
