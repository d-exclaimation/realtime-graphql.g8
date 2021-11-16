//
//  Subscription.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.schema

import $package$.implicits.Implicits.materializer
import $package$.services.Counter
import io.github.dexclaimation.soda.schema.{Dfe, SodaSubscription}
import sangria.schema.{Action, IntType}
import sangria.streaming.akkaStreams._

object Subscription extends SodaSubscription[Counter, Unit] {
  def definition: Def = { t =>
    t.stream("feed", IntType) {
      case Dfe(_, _, c) => c.feed.map(Action(_))
    }
  }
}
