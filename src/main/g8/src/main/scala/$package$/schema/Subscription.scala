//
//  Subscription.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.schema

import io.github.dexclaimation.soda.schema.{Dfe, SodaSubscription}
import $package$.services.Counter
import $package$.implicits.Implicits
import sangria.schema.Action
import sangria.streaming.akkaStreams._
import sangria.schema.{IntType}

object Subscription extends SodaSubscription[Counter, Unit] {
  def definition: Def = { t =>
    t.field("feed", IntType) {
      case Dfe(_, _, c) => c.feed.map(Action[Counter, Int](_))
    }
  }
}
