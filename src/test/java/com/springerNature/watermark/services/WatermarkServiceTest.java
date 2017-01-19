package com.springerNature.watermark.services;

import com.springerNautre.watermark.dto.Content;
import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;
import com.springerNautre.watermark.services.WatermarkService;
import com.springerNautre.watermark.services.WatermarkServiceImpl;
import com.springerNautre.watermark.services.WatermarkStorageService;
import com.springerNautre.watermark.services.WatermarkStorageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.springerNature.watermark.WatermarkTestUtils.getDocumentForSubmission;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ganeshnagarajan on 1/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class WatermarkServiceTest {

    @Mock
    WatermarkStorageService watermarkStorageService;

    @InjectMocks
    private WatermarkServiceImpl watermarkService;

    @Test
    public void shouldAddDocument(){
        doNothing().when(watermarkStorageService).addDocument(anyLong(),anyObject());
        Document doc = new Document();
        doc.setContent(Content.BOOK);
        doc.setAuthor("Test Author");
        doc.setTitle("Test title");
        assertEquals(watermarkService.addDocument(doc), 1001);
    }

    @Test
    public void shouldReturnJobStatus() throws WatermarkException {
        when(watermarkStorageService.getStatus(anyLong())).thenReturn(JobStatus.FINISHED);
        assertEquals(watermarkService.getStatus(1001),JobStatus.FINISHED);
    }


    @Test
    public void shouldReturnDocument() throws WatermarkException {
        when(watermarkStorageService.getWatermarkedDocument(anyLong())).thenReturn(getDocumentForSubmission());
        Document doc = watermarkService.getWatermarkedDocument(1000);
        assertNotNull(doc);
        assertEquals(doc.getAuthor(),"Steve Jobs");
        assertEquals(doc.getTitle(),"Apple");
    }
}
