//
//  Config.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 8:45 PM.
//

package $package$.constants

import akka.http.scaladsl.model.headers.HttpOrigin
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.model.HttpOriginMatcher
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings

object Config {
  /** CORS Setup for allowing Apollo Sandbox */
  val cors = CorsSettings
    .defaultSettings
    .withAllowedOrigins(HttpOriginMatcher(HttpOrigin("https://studio.apollographql.com")))
    .withAllowCredentials(true)
}