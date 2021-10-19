//
//  Subscription.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.schema

object Subscription extends SodaSubscription[Counter, Unit] {
  def definition: Def = { t =>
    t.stream("feed", IntType) {
      case Dfe(_, _, c) => c.feed.map(Action(_))
    }
  }
}
