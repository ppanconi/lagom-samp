package it.panks.report.impl;

import it.panks.report.api.ItemReportMessage;
import it.panks.report.api.ReportMessage;
import it.panks.report.api.ReportService;
import org.junit.Test;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.withServer;
import static org.assertj.core.api.Assertions.assertThat;


public class ReportServiceImplTest {

    @Test
    public void report() throws Exception {

        withServer(defaultSetup().withCluster(true), server -> {


            ReportService service = server.client(ReportService.class);
            ReportMessage result = service.report().invoke().toCompletableFuture().get();

            assertThat(result.items()).containsOnly(
                    ItemReportMessage.builder().message("ciao").id("panks").build()
            );

        });
    }

}