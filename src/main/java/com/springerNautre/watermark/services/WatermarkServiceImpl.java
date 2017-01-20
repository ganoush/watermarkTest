package com.springerNautre.watermark.services;

import com.springerNautre.watermark.dto.Content;
import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ganeshnagarajan on 1/12/17.
 */
@Service
public class WatermarkServiceImpl implements WatermarkService {

    @Autowired
    WatermarkStorageService watermarkStorageService;

    //Counter to get the ticket
    AtomicLong count = new AtomicLong(1000);

    private static final Logger log = LoggerFactory.getLogger(WatermarkServiceImpl.class);

    /**
     * This method submits the given document to the WatermarkStorageService. It uses the atomicLong to generate the ticket.
     * @param document
     * @return ticket
     */
    @Override
    public long addDocument(Document document) {
        long ticket = count.incrementAndGet();
        if (Optional.ofNullable(document.getTopic()).isPresent()){
            document.setContent(Content.BOOK);
        } else {
            document.setContent(Content.JOURNAL);
        }
        watermarkStorageService.addDocument(ticket, document);
        return ticket;
    }

    /**
     *  This method fetches the submitted job status for the fiven ticket
     * @param ticket
     * @return JObStatus
     */
    @Override
    public JobStatus getStatus(long ticket) {
        JobStatus status = null;
        try {
            status = watermarkStorageService.getStatus(ticket);
        } catch (WatermarkException e) {
            log.error("Job status not found for the ticket " + ticket);
        }
        return status;
    }

    /**
     *  This method fetches the watermarked document for the given document
     * @param ticket
     * @return
     */
    @Override
    public Document getWatermarkedDocument(long ticket){
        Document doc = null;
        try {
            return watermarkStorageService.getWatermarkedDocument(ticket);
        } catch (WatermarkException ex) {
            log.error("Watermarked Document not found for the ticket : " + ticket + " : " + ex.getMessage());
        }
        return doc;
    }
}
