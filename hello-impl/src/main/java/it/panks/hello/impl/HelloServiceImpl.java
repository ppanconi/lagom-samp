/*
 * Copyright (C) 2016-2017 Lightbend Inc. <https://www.lightbend.com>
 */
package it.panks.hello.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import it.panks.hello.api.GreetingApiEvent;
import it.panks.hello.api.GreetingMessage;
import it.panks.hello.api.HelloService;
import it.panks.hello.impl.HelloCommand.Hello;
import it.panks.hello.impl.HelloCommand.UseGreetingMessage;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Implementation of the HelloService.
 */
public class HelloServiceImpl implements HelloService {

  private final PersistentEntityRegistry persistentEntityRegistry;

  @Inject
  public HelloServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(HelloEntity.class);
  }

  @Override
  public ServiceCall<NotUsed, String> hello(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<HelloCommand> ref = persistentEntityRegistry.refFor(HelloEntity.class, id);
      // Ask the entity the Hello command.
      return ref.ask(new Hello(id, Optional.empty()));
    };
  }

  @Override
  public ServiceCall<GreetingMessage, Done> useGreeting(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<HelloCommand> ref = persistentEntityRegistry.refFor(HelloEntity.class, id);
      // Tell the entity to use the greeting message specified.
      return ref.ask(new UseGreetingMessage(request.message));
    };

  }

  @Override
  public Topic<GreetingApiEvent> greetingsTopic() {
    return TopicProducer. singleStreamWithOffset(offset ->
      persistentEntityRegistry.eventStream(HelloEvent.TAG, offset).map(param ->
              Pair.create(toGreetingApiEvent(param.first()), param.second())
      )
    );
  }

  private GreetingApiEvent toGreetingApiEvent(HelloEvent event) {

    System.out.println("here " + event);

    if (event instanceof HelloEvent.GreetingMessageChanged) {
      HelloEvent.GreetingMessageChanged helloEvent = (HelloEvent.GreetingMessageChanged) event;
      return new GreetingApiEvent(helloEvent.message, helloEvent.id);
    }
    throw new RuntimeException("Not actual suppoerted HelloEvent " + event);
  }

}
