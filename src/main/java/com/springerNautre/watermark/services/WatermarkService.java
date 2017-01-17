package com.springerNautre.watermark.services;

import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;

/**
 * Created by ganeshnagarajan on 1/12/17.
 */
public interface WatermarkService {
    long addDocument(Document document);
    JobStatus getStatus(long id);
    Document getWatermarkedDocument(long id);
}
