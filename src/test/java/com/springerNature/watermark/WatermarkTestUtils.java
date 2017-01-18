package com.springerNature.watermark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springerNautre.watermark.dto.Content;
import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.DocumentStore;
import com.springerNautre.watermark.dto.JobStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ganeshnagarajan on 1/16/17.
 */
public class WatermarkTestUtils {
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getDocumentForSubmission(){
        Document document = new Document();
        document.setTitle("Apple");
        document.setAuthor("Steve Jobs");
        return document;
    }

    public static Document getDocumentForSubmissionNoTitle(){
        Document document = new Document();
        document.setTopic("Science");
        document.setAuthor("Steve Jobs");
        return document;
    }

    public static Map initializeDcouments(){
        ConcurrentHashMap<Long, DocumentStore> map = new ConcurrentHashMap();
        DocumentStore store = new DocumentStore();
        Document doc = new Document();
        doc.setTitle("Tell me Your Dreams");
        doc.setTopic("Fiction");
        doc.setAuthor("Sidney Sheldon");
        doc.setContent(Content.BOOK);
        store.setDocument(doc);
        store.setTicket(1000L);
        store.setJobStatus(JobStatus.FINISHED);
        map.put(1000L,store);
        return map;
    }
}
