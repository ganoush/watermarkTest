package com.springerNautre.watermark.services;

import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.DocumentStore;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ganeshnagarajan on 1/16/17.
 */
@Service
public class WatermarkStorageServiceImpl implements WatermarkStorageService{

    //Data structure to store the Documents. ConcurrentHashMap for concurrent access
    static Map<Long, DocumentStore> documents = new ConcurrentHashMap<>();

    /**
     * Method that puts the given document in the documentstore. The job status would be pending at the time of submission.
     * For every 30 sec setWatermark will be invoked to watermark the documents.
     * @param id
     * @param document
     */
    @Override
    public void addDocument(long id, Document document) {
        DocumentStore docStore = new DocumentStore();
        docStore.setDocument(document);
        docStore.setTicket(id);
        docStore.setJobStatus(JobStatus.PENDING);
        documents.putIfAbsent(id, docStore);
    }

    /**
     * Method to fetch document status for the given id. Returns the Job status if present otherwise returns NOT_FOUND
     * @param id
     * @return JobStatus
     */
    @Override
    public JobStatus getStatus(long id) throws WatermarkException {
        DocumentStore docStore = documents.get(id);
        Optional document = Optional.ofNullable(docStore);
        if (document.isPresent()) {
            return docStore.getJobStatus();
        } else {
            throw new WatermarkException("Job Status not found");
        }
    }

    /**
     *  This method fetches the document from the store if the status is finished.
     * @param id
     * @return Document
     * @throws WatermarkException
     */
    @Override
    public Document getWatermarkedDocument(long id) throws WatermarkException {
        DocumentStore docStore = documents.get(id);
        Optional document = Optional.ofNullable(docStore);
        if (document.isPresent() && docStore.getJobStatus().equals(JobStatus.FINISHED)) {
            return docStore.getDocument();
        } else {
            throw new WatermarkException("Document not found");
        }
    }

    /*
    *  Method to simulate batch Job. This Spring scheduler method gets invoked every 30 seconds and
    *  set the Job status to finished for any of the Jobs that are in status PENDING
    * */
    @Scheduled(fixedDelay = 30000)
    public void setWatermark() {
        documents.forEach((ticket,doc)->{
            if (doc.getJobStatus() != JobStatus.FINISHED){
                doc.getDocument().setWatermark();
                doc.setJobStatus(JobStatus.FINISHED);
            }
        });
    }
}
