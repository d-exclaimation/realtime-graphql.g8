//
//  Implicits.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 5:55 PM.
//

package $package$.implicits

import akka.actor.typed.{ActorSystem, SpawnProtocol}
import akka.stream.Materializer
import io.github.dexclaimation.overlayer.OverTransportLayer

import scala.concurrent.ExecutionContext

object Implicits {
  implicit val system: ActorSystem[SpawnProtocol.Command] = OverTransportLayer.makeSystem("graphscale")
  implicit val materializer: Materializer = Materializer.createMaterializer(system)
  implicit val executionContext: ExecutionContext = system.executionContext
}
