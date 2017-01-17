package com.springerNature.watermark;

import com.springerNautre.watermark.WatermarkApplication;
import com.springerNautre.watermark.dto.Document;
import com.springerNautre.watermark.dto.JobStatus;
import com.springerNautre.watermark.services.WatermarkService;
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

import static com.springerNature.watermark.WatermarkTestUtils.asJsonString;
import static com.springerNautre.watermark.dto.JobStatus.NOT_FOUND;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Mock
    @Autowired
    private WatermarkService watermarkService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testForDocumentSubmissionWithNoTitle() throws Exception {
        this.mockMvc.perform(post("/api/submit").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(WatermarkTestUtils.getDocumentForSubmissionNoTitle())))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void testForDocumentSubmission() throws Exception {
        this.mockMvc.perform(post("/api/submit").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(WatermarkTestUtils.getDocumentForSubmission())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1001"));
    }

    @Test
    public void testForDocumentStatusNotFound() throws Exception {
        //when(watermarkService.getStatus(anyInt())).thenReturn(JobStatus.PENDING);
        this.mockMvc.perform(get("/api/status/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(NOT_FOUND)));
    }

    @Test
    public void testForWatermarkedDocument() throws Exception {
        this.mockMvc.perform(get("/api/getDoc/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
