/*
 * Copyright (C) 2016-2017 Lightbend Inc. <https://www.lightbend.com>
 */
package it.panks.hello.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.concurrent.Immutable;

/**
 * This interface defines all the events that the Hello entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface HelloEvent extends Jsonable, AggregateEvent<HelloEvent> {

//  int NUM_SHARDS = 4;
  AggregateEventTag<HelloEvent> TAG = AggregateEventTag.of(HelloEvent.class);

  @Override
  default AggregateEventTagger<HelloEvent> aggregateTag() {
    return TAG;
  }

  /**
   * An event that represents a change in greeting message.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class GreetingMessageChanged implements HelloEvent {
    public final String message;
    public final String id;

    @JsonCreator
    public GreetingMessageChanged(String message, String id) {
      this.message = Preconditions.checkNotNull(message, "message");
      this.id = Preconditions.checkNotNull(id, "id");
    }

    public String getMessage() {
      return message;
    }

    public String getId() {
      return id;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      GreetingMessageChanged that = (GreetingMessageChanged) o;

      if (message != null ? !message.equals(that.message) : that.message != null) return false;
      return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
      int result = message != null ? message.hashCode() : 0;
      result = 31 * result + (id != null ? id.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "GreetingMessageChanged{" +
              "message='" + message + '\'' +
              ", id='" + id + '\'' +
              '}';
    }
  }
}
