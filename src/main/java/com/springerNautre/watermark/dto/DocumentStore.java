package com.springerNautre.watermark.dto;

/**
 * Created by ganeshnagarajan on 1/16/17.
 */
public class DocumentStore{
    private long ticket;
    private Document document;
    private JobStatus jobStatus;

    public long getTicket() {
        return ticket;
    }

    public void setTicket(long ticket) {
        this.ticket = ticket;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }
}
