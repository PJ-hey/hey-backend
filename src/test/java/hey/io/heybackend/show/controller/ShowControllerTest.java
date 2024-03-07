package hey.io.heybackend.show.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.heybackend.show.ShowInfoFixture;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.service.ShowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShowService showService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createShow() throws Exception {

        CreateShowRequest request = ShowInfoFixture.getCreateRequestInfo();
        ShowResponse response = ShowInfoFixture.getCreateShowResponseInfo(request);
        when(showService.createShow(any())).thenReturn(response);
        mockMvc.perform(post("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    void getShow() throws Exception {

        when(showService.getShow(any())).thenReturn(Page.empty());

        mockMvc.perform(get("/show")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    void getShowInfo() throws Exception {

        CreateShowRequest request = ShowInfoFixture.getCreateRequestInfo();
        ShowResponse response = ShowInfoFixture.getCreateShowResponseInfo(request);

        when(showService.getShowInfo(any())).thenReturn(response);

        mockMvc.perform(get("/show/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateShow() throws Exception {

        UpdateShowRequest request = ShowInfoFixture.getUpdateRequestInfo();
        ShowResponse response = ShowInfoFixture.getUpdateShowResponseInfo(request);
        when(showService.updateShow(eq(1L), any())).thenReturn(response);
        mockMvc.perform(put("/show/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void deleteShow() throws Exception {

        doNothing().when(showService).deleteShow(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/show/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
