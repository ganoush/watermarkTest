package com.springerNature.watermark.controller;

import com.springerNature.watermark.WatermarkTestUtils;
import com.springerNautre.watermark.WatermarkApplication;
import com.springerNautre.watermark.services.WatermarkService;
import com.springerNautre.watermark.services.WatermarkStorageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static com.springerNature.watermark.WatermarkTestUtils.asJsonString;
import static com.springerNautre.watermark.dto.JobStatus.FINISHED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ganeshnagarajan on 1/16/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WatermarkApplication.class)
@WebAppConfiguration
public class WatermarkControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws IllegalAccessException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        //Preload with one document
        final Set<Field> fields = org.powermock.reflect.Whitebox.getAllStaticFields(WatermarkStorageServiceImpl.class);
        for (final Field field : fields) {
            if (Map.class.equals(field.getType())) {
                field.setAccessible(true);
                field.set(WatermarkStorageServiceImpl.class, WatermarkTestUtils.initializeDcouments());
            }
        }
    }

    @Test
    public void shouldFailForDocumentSubmissionWithNoTitle() throws Exception {
        this.mockMvc.perform(post("/api/submit").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(WatermarkTestUtils.getDocumentForSubmissionNoTitle())))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void shouldSubmitValidDocumentSuccessfully() throws Exception {
        this.mockMvc.perform(post("/api/submit").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(WatermarkTestUtils.getDocumentForSubmission())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1001"));
    }

    @Test
    public void shouldReturnNotFoundForInvalidTicket() throws Exception {
        this.mockMvc.perform(get("/api/status/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturnJobStatusForValidTicket() throws Exception {
        this.mockMvc.perform(get("/api/status/1000").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(FINISHED)));
    }

    @Test
    public void shouldNotReturnContentForValidTicket() throws Exception {
        this.mockMvc.perform(get("/api/getDoc/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturnContentForValidTicket() throws Exception {
        this.mockMvc.perform(get("/api/getDoc/1000").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("BOOK"))
                .andExpect(jsonPath("$.author").value("Sidney Sheldon"))
                .andExpect(jsonPath("$.topic").value("Fiction"))
                .andExpect(jsonPath("$.title").value("Tell me Your Dreams"));
    }
}
