package com.github.imliar.getstream.client.util

import com.twitter.util.{Return, Throw, Future => TwFuture, Promise => TwPromise, Try => TwTry}

import scala.util.{Failure, Success, Try => ScTry}
import scala.concurrent.{ExecutionContext, Future => ScFuture, Promise => scPromise}

/**
 * Twitter <=> Scala future convert
 */
object Twitter {

  implicit class TwFutureToScala[T](val tf: TwFuture[T]) extends AnyVal {
    def asScala: ScFuture[T] = {
      val prom = scPromise[T]()

      tf.onSuccess { prom success _ }
      tf.onFailure { prom failure _ }

      prom.future
    }
  }

  implicit class ScFutureToTwitter[T](val sf: ScFuture[T]) extends AnyVal {
    def asTwitter(implicit ec: ExecutionContext): TwFuture[T] = {
      val prom = TwPromise[T]()

      sf.onComplete {
        case Success(value) => {
          prom.setValue(value)
        }
        case Failure(exception) => prom.setException(exception)
      }

      prom
    }
  }

  implicit class TwTryToScala[T](val tt: TwTry[T]) extends AnyVal {
    def asScala: ScTry[T] = tt match {
      case Throw(t) => new util.Failure(t)
      case Return(r) => new util.Success(r)
    }
  }

  implicit class ScTryToTwitter[T](val st: ScTry[T]) extends AnyVal {
    def asTwitter: TwTry[T] = st match {
      case util.Failure(t) => Throw(t)
      case util.Success(r) => Return(r)
    }
  }

}