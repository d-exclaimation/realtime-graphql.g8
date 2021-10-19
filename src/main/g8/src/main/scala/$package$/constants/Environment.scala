//
//  Environment.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 5:53 PM.
//

package $package$.constants

object Environment {
  val __port__ = sys.env
    .get("PORT")
    .flatMap(_.toIntOption)
    .getOrElse(4000)

  val __prod__ = sys.env
    .get("RUNTIME_ENV")
    .contains("PRODUCTION")

  val __endpoint__ =
    (if (__prod__) sys.env("ENDPOINT") else s"http://localhost:${__port__}") ++ "/graphql"
}
