package hey.io.heybackend.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.heybackend.report.ReportInfoFixture;
import hey.io.heybackend.report.dtos.request.ReportRequest;
import hey.io.heybackend.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void reportShow() throws Exception {

        ReportRequest request = ReportInfoFixture.getReportRequestInfo();

        doNothing().when(reportService).reportShow(any(), any());

        mockMvc.perform(post("/show/1/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void reportArtist() throws Exception {

        ReportRequest request = ReportInfoFixture.getReportRequestInfo();

        doNothing().when(reportService).reportArtist(any(), any());

        mockMvc.perform(post("/artist/1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }
}
