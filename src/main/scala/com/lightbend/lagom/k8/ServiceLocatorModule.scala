package com.lightbend.lagom.k8

import com.lightbend.lagom.javadsl.api.ServiceLocator
import play.api.{Configuration, Environment, Mode}
import play.api.inject.{Binding, Module}

import javax.inject.Singleton

/**
 * This module binds the ServiceLocator interface from Lagom to the `K8RServiceLocator`
 * The `K8RServiceLocator` is only bound if the application has been started in `Prod` mode.
 * In `Dev` mode the embedded service locator of Lagom is used.
 */
class ServiceLocatorModule extends Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] =
    if (environment.mode == Mode.Prod)
      Seq(bind[ServiceLocator].to[K8ServiceLocator].in[Singleton])
    else
      Seq.empty
}
