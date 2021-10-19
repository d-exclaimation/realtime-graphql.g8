//
//  Query.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.schema

import io.github.dexclaimation.soda.schema.{Dfe, SodaQuery}
import $package$.services.Counter
import sangria.schema.{IntType}

object Query extends SodaQuery[Counter, Unit] {
  def definition: Def = { t =>
    t.field("current", IntType) {
      case Dfe(_, _, c) => c.current
    }
  }
}
