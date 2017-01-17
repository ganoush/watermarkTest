package com.springerNautre.watermark.services;

import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;

/**
 * Created by ganeshnagarajan on 1/16/17.
 */
public interface WatermarkStorageService {
    void addDocument(long id, Document document);
    JobStatus getStatus(long id);
    Document getWatermarkedDocument(long id) throws WatermarkException;
}
