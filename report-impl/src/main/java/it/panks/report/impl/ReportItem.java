package it.panks.report.impl;

import it.panks.report.api.ItemReportMessage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ReportItem {

    @Id
    private String id;

    @NotNull
    private String message;

    public ReportItem() {
    }

    public ReportItem(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ItemReportMessage toMessage() {

        return ItemReportMessage.builder()
                    .id(getId())
                    .message(getMessage())
                .build();
    }

}
