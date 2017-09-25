package it.panks.report.impl;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Flow;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.jpa.JpaSession;
import it.panks.hello.api.GreetingApiEvent;
import it.panks.hello.api.HelloService;
import it.panks.report.api.ReportMessage;
import it.panks.report.api.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

//import javax.persistence.EntityManager;

public class ReportServiceImpl implements ReportService {

    private static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final JpaSession jpaSession;
    private final HelloService helloService;

    @Inject
    public ReportServiceImpl(JpaSession jpaSession, HelloService helloService) {
        this.jpaSession = jpaSession;
        this.helloService = helloService;

        helloService.greetingsTopic().subscribe()
                .atLeastOnce(
                Flow.<GreetingApiEvent>create().mapAsync(1, event -> {
                            logger.info(" ______________________________________ GreetingApiEvent received " + event);
                            return jpaSession.withTransaction(entityManager ->
                            entityManager.merge(makeReportItemFromEvent(event))).thenApply(reportItem -> Done.getInstance());
                        }
                ));

        logger.info(" ______________________________________________________ greetingsTopic subscribed ");
    }

    @Override
    public ServiceCall<NotUsed, ReportMessage> report() {

//        return
//                req -> completedFuture(ReportMessage.builder()
//                    .addItems(ItemReportMessage.builder().message("ciao").id("panks").build())
//                .build());

        return req -> jpaSession.withTransaction(this::queryItems)
                    .thenApply(items ->
                            ReportMessage.builder()
                                    .items(items.stream().map(item -> item.toMessage()).collect(Collectors.toList()))
                                    .build()
                    );
    }

    private List<ReportItem> queryItems(EntityManager entityManager) {

        return entityManager
                .createQuery("SELECT" +
                                " NEW it.panks.report.impl.ReportItem(i.id, i.message)" +
                                " FROM ReportItem i",
                            ReportItem.class
                )
                .getResultList();
    }

    private ReportItem makeReportItemFromEvent(GreetingApiEvent event) {
        ReportItem item = new ReportItem(event.getId(), event.getMessage());
        return item;
    }
}
