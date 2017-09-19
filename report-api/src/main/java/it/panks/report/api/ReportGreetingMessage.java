package it.panks.report.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;
import java.util.List;

/**
 * Created by paolo on 19/09/17.
 */
@Immutable
@JsonDeserialize
public class ReportGreetingMessage {

    //TODO use immutable type
    public final List<ItemReportGreetingMessage> items;

    public ReportGreetingMessage(List<ItemReportGreetingMessage> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportGreetingMessage that = (ReportGreetingMessage) o;

        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReportGreetingMessage{" +
                "items=" + items +
                '}';
    }
}
