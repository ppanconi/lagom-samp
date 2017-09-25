package it.panks.hello.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.annotation.concurrent.Immutable;

@Immutable
@JsonDeserialize
@JsonSerialize
public final class GreetingApiEvent {

    private final String message;
    private final String id;

    public GreetingApiEvent(String message, String id) {
        this.message = message;
        this.id = id;
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

        GreetingApiEvent that = (GreetingApiEvent) o;

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
        return "GreetingApiEvent{" +
                "message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
