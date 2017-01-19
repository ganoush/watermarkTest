package com.springerNature.watermark.services;

import com.springerNature.watermark.WatermarkTestUtils;
import com.springerNautre.watermark.dto.Content;
import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.exception.WatermarkException;
import com.springerNautre.watermark.services.WatermarkServiceImpl;
import com.springerNautre.watermark.services.WatermarkStorageService;
import com.springerNautre.watermark.services.WatermarkStorageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ganeshnagarajan on 1/17/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class WatermarkStorageServiceTest {

    @InjectMocks
    WatermarkStorageServiceImpl watermarkStorageService;

    @Before
    public void setUp() throws IllegalAccessException {
        final Set<Field> fields = org.powermock.reflect.Whitebox.getAllStaticFields(WatermarkStorageServiceImpl.class);
        for (final Field field : fields) {
            if (Map.class.equals(field.getType())) {
                field.setAccessible(true);
                field.set(WatermarkStorageServiceImpl.class, WatermarkTestUtils.initializeDcouments());
            }
        }
    }

    @Test
    public void shouldAddDocument() throws WatermarkException {
        Document doc = new Document();
        doc.setContent(Content.BOOK);
        doc.setAuthor("Test Author");
        doc.setTitle("Test title");
        watermarkStorageService.addDocument(1001,doc);
        assertEquals(watermarkStorageService.getStatus(1001), JobStatus.PENDING);
    }

    @Test
    public void shouldReturnJobStatus() throws WatermarkException {
        assertEquals(watermarkStorageService.getStatus(1000), JobStatus.FINISHED);
    }

    @Test
    public void shouldReturnDocument() throws WatermarkException {
        Document doc = watermarkStorageService.getWatermarkedDocument(1000);
        assertNotNull(doc);
        assertEquals(doc.getAuthor(), "Sidney Sheldon");
        assertEquals(doc.getTitle(), "Tell me Your Dreams");
        assertEquals(doc.getTopic(), "Fiction");
    }
}
