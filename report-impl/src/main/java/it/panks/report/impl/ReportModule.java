package it.panks.report.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import it.panks.hello.api.HelloService;
import it.panks.report.api.ReportService;

public class ReportModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(ReportService.class, ReportServiceImpl.class);
        bindClient(HelloService.class);
    }
}
