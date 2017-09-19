package it.panks.report.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import javax.annotation.concurrent.Immutable;

/**
 * Created by paolo on 19/09/17.
 */
@Immutable
@JsonDeserialize
public class ItemReportGreetingMessage {

    public final String id;
    public final String message;

    public ItemReportGreetingMessage(String id, String message) {
        this.id = Preconditions.checkNotNull(id, "id");
        this.message = Preconditions.checkNotNull(message, "message");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemReportGreetingMessage that = (ItemReportGreetingMessage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemReportGreetingMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
