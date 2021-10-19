//
//  Counter.scala
//  realtime-graphql.g8
//
//  Created by d-exclaimation on 6:00 PM.
//

package $package$.services

import akka.NotUsed
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{BroadcastHub, Keep, Source}
import akka.stream.typed.scaladsl.ActorSource
import $package$.implicits.Implicits._

import java.util.concurrent.atomic.AtomicInteger

class Counter {
  val atomic = new AtomicInteger()

  private val (ref, source) = ActorSource
    .actorRef[Int](
      PartialFunction.empty,
      PartialFunction.empty,
      256,
      OverflowStrategy.dropHead
    )
    .toMat(BroadcastHub.sink(256))(Keep.both)
    .run()

  def increment(): Int = {
    val res = atomic.incrementAndGet()
    ref ! res
    res
  }

  def current: Int = atomic.get()

  def feed: Source[Int, NotUsed] = source
}