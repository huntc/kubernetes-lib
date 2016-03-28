package com.lightbend.lagom.k8

import java.net.URI
import java.util.Optional
import java.util.concurrent.CompletionStage
import java.util.function.{Function => JFunction}
import javax.inject.Inject

import com.lightbend.lagom.javadsl.api.ServiceLocator
import play.api.libs.ws.WSClient

import scala.compat.java8.FutureConverters._
import scala.compat.java8.OptionConverters._
import scala.concurrent.{ExecutionContext, Future}

/**
 * ConductRServiceLocator implements Lagom's ServiceLocator by using the ConductR Service Locator.
 */
class K8ServiceLocator @Inject()(implicit ec: ExecutionContext, ws: WSClient) extends ServiceLocator {

  private def locateAsScala(name: String): Future[Option[URI]] =
    Future.successful(None)

  override def locate(name: String): CompletionStage[Optional[URI]] =
    locateAsScala(name).map(_.asJava).toJava

  override def doWithService[T](name: String, block: JFunction[URI, CompletionStage[T]]): CompletionStage[Optional[T]] =
    locateAsScala(name).flatMap { uriOpt =>
      uriOpt.fold(Future.successful(Optional.empty[T])) { uri =>
        block.apply(uri)
          .toScala
          .map(v => Optional.of(v))
      }
    }.toJava
}
