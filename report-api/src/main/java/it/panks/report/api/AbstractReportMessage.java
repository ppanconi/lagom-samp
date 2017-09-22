package it.panks.report.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.immutable.ImmutableStyle;
import org.immutables.value.Value;

import java.util.List;

/**
 * Created by paolo on 19/09/17.
 */
@Value.Immutable
@ImmutableStyle
@JsonDeserialize
public interface AbstractReportMessage {

    List<ItemReportMessage> items();

}
