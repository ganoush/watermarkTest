package com.springerNature.watermark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springerNautre.watermark.dto.Document;

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

}
