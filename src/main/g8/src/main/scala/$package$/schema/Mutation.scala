//
//  Mutation.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.schema

import io.github.dexclaimation.soda.schema.{Dfe, SodaMutation}
import $package$.services.Counter
import sangria.schema.{IntType}

object Mutation extends SodaMutation[Counter, Unit] {
  def definition: Def = { t =>
    t.field("increment", IntType) {
      case Dfe(_, _, c) => c.increment()
    }
  }
}
