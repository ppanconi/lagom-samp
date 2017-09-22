package it.panks.report.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.jpa.JpaSession;
import it.panks.report.api.ItemReportMessage;
import it.panks.report.api.ReportMessage;
import it.panks.report.api.ReportService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

//import javax.persistence.EntityManager;

public class ReportServiceImpl implements ReportService {

    private final JpaSession jpaSession;

    @Inject
    public ReportServiceImpl(JpaSession jpaSession) {
        this.jpaSession = jpaSession;
    }

    @Override
    public ServiceCall<NotUsed, ReportMessage> report() {

        return req -> completedFuture(ReportMessage.builder()
                    .addItems(ItemReportMessage.builder().message("ciao").id("panks").build())
                .build());

//        return req -> jpaSession.withTransaction(this::queryItems)
//                    .thenApply(items ->
//                            ReportMessage.builder()
//                                    .items(items.stream().map(item -> item.toMessage()).collect(Collectors.toList()))
//                                    .build()
//                    );
    }

//    private List<ReportItem> queryItems(EntityManager entityManager) {
//
//        return entityManager
//                .createQuery("SELECT" +
//                                " NEW it.panks.report.impl.ReportItem(i.id, i.message)" +
//                                " FROM ReportItem i",
//                            ReportItem.class
//                )
//                .getResultList();
//    }
}
