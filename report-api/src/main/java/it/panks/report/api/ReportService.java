package it.panks.report.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;


public interface ReportService extends Service {

    ServiceCall<NotUsed, ReportMessage> report();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("report").withCalls(
                pathCall("/api/report/",  this::report)
        ).withAutoAcl(true);
        // @formatter:on
    }


}
