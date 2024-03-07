package hey.io.heybackend.show.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.entities.PriceInfo;
import hey.io.heybackend.show.entities.TicketSeller;
import hey.io.heybackend.show.service.ShowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShowService showService;

    @Test
    void createShow() throws Exception {
        CreateShowRequest request = CreateShowRequest.builder()
                .name("Show")
                .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                .urlId("test-show")
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .images(Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/show")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Show"))
                .andExpect(jsonPath("$.data.priceInfos[0].type").value("Regular"))
                .andExpect(jsonPath("$.data.priceInfos[0].price").value(10000))
                .andExpect(jsonPath("$.data.priceInfos[1].type").value("VIP"))
                .andExpect(jsonPath("$.data.priceInfos[1].price").value(20000))
                .andExpect(jsonPath("$.data.ticketSellers[0].name").value("yes24"))
                .andExpect(jsonPath("$.data.ticketSellers[0].baseUrl").value("https://yes24.com"))
                .andExpect(jsonPath("$.data.ticketSellers[0].icon").value("yes24-icon.png"))
                .andExpect(jsonPath("$.data.urlId").value("test-show"))
                .andExpect(jsonPath("$.data.ticketOpenTime").value("2024-03-07T09:00:00"))
                .andExpect(jsonPath("$.data.date").value("2024-04-01T19:00:00"))
                .andExpect(jsonPath("$.data.strictedAge").value(18))
                .andExpect(jsonPath("$.data.runningTime").value("02:30:00"))
                .andExpect(jsonPath("$.data.place").value("Theater"))
                .andExpect(jsonPath("$.data.type").value("Concert"))
                .andExpect(jsonPath("$.data.genre").value("Pop"))
                .andExpect(jsonPath("$.data.poster").value("image1.jpg"))
                .andExpect(jsonPath("$.data.detailImages[0]").value("image2.jpg"))
                .andExpect(jsonPath("$.data.detailImages[1]").value("image3.jpg"))
                .andExpect(jsonPath("$.data.isConfirmed").value(true));
    }

    @Test
    void getShow() throws Exception {

        List<ShowResponse> response = new ArrayList<>();
        for(int i = 1; i <= 2; i++) {
            ShowResponse showResponse = ShowResponse.builder()
                    .id(1L)
                    .name("Show2")
                    .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                    .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                    .urlId("test-show")
                    .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                    .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                    .strictedAge(18)
                    .runningTime(LocalTime.of(2, 30))
                    .place("Theater")
                    .type("Concert")
                    .genre("Pop")
                    .poster("image1.jpg")
                    .detailImages(Arrays.asList("image2.jpg", "image3.jpg"))
                    .isConfirmed(true)
                    .build();
            response.add(showResponse);
        }
        Pageable pageable = PageRequest.of(0, 20);
        Page<ShowResponse> pageResult = new PageImpl<>(response, pageable, 2);

        when(showService.getShow(pageable)).thenReturn(pageResult);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/show")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Show2"))
                .andExpect(jsonPath("$.data.content[0].priceInfos[0].type").value("Regular"))
                .andExpect(jsonPath("$.data.content[0].priceInfos[0].price").value(10000))
                .andExpect(jsonPath("$.data.content[0].priceInfos[1].type").value("VIP"))
                .andExpect(jsonPath("$.data.content[0].priceInfos[1].price").value(20000))
                .andExpect(jsonPath("$.data.content[0].ticketSellers[0].name").value("yes24"))
                .andExpect(jsonPath("$.data.content[0].ticketSellers[0].baseUrl").value("https://yes24.com"))
                .andExpect(jsonPath("$.data.content[0].ticketSellers[0].icon").value("yes24-icon.png"))
                .andExpect(jsonPath("$.data.content[0].urlId").value("test-show"))
                .andExpect(jsonPath("$.data.content[0].ticketOpenTime").value("2024-03-07T09:00:00"))
                .andExpect(jsonPath("$.data.content[0].date").value("2024-04-01T19:00:00"))
                .andExpect(jsonPath("$.data.content[0].strictedAge").value(18))
                .andExpect(jsonPath("$.data.content[0].runningTime").value("02:30:00"))
                .andExpect(jsonPath("$.data.content[0].place").value("Theater"))
                .andExpect(jsonPath("$.data.content[0].type").value("Concert"))
                .andExpect(jsonPath("$.data.content[0].genre").value("Pop"))
                .andExpect(jsonPath("$.data.content[0].poster").value("image1.jpg"))
                .andExpect(jsonPath("$.data.content[0].detailImages[0]").value("image2.jpg"))
                .andExpect(jsonPath("$.data.content[0].detailImages[1]").value("image3.jpg"))
                .andExpect(jsonPath("$.data.content[0].isConfirmed").value(true));
    }

    @Test
    void getShowInfo() throws Exception {
        ShowResponse showResponse = ShowResponse.builder()
                .id(1L)
                .name("Show2")
                .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                .urlId("test-show")
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .poster("image1.jpg")
                .detailImages(Arrays.asList("image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();

        when(showService.getShowInfo(anyLong())).thenReturn(showResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/show/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Show2"))
                .andExpect(jsonPath("$.data.priceInfos[0].type").value("Regular"))
                .andExpect(jsonPath("$.data.priceInfos[0].price").value(10000))
                .andExpect(jsonPath("$.data.priceInfos[1].type").value("VIP"))
                .andExpect(jsonPath("$.data.priceInfos[1].price").value(20000))
                .andExpect(jsonPath("$.data.ticketSellers[0].name").value("yes24"))
                .andExpect(jsonPath("$.data.ticketSellers[0].baseUrl").value("https://yes24.com"))
                .andExpect(jsonPath("$.data.ticketSellers[0].icon").value("yes24-icon.png"))
                .andExpect(jsonPath("$.data.urlId").value("test-show"))
                .andExpect(jsonPath("$.data.ticketOpenTime").value("2024-03-07T09:00:00"))
                .andExpect(jsonPath("$.data.date").value("2024-04-01T19:00:00"))
                .andExpect(jsonPath("$.data.strictedAge").value(18))
                .andExpect(jsonPath("$.data.runningTime").value("02:30:00"))
                .andExpect(jsonPath("$.data.place").value("Theater"))
                .andExpect(jsonPath("$.data.type").value("Concert"))
                .andExpect(jsonPath("$.data.genre").value("Pop"))
                .andExpect(jsonPath("$.data.poster").value("image1.jpg"))
                .andExpect(jsonPath("$.data.detailImages[0]").value("image2.jpg"))
                .andExpect(jsonPath("$.data.detailImages[1]").value("image3.jpg"))
                .andExpect(jsonPath("$.data.isConfirmed").value(true));
    }

    @Test
    void updateShow() throws Exception {

        UpdateShowRequest request = UpdateShowRequest.builder()
                .name("Show2")
                .ticketOpenTime(LocalDateTime.of(2024, 3, 7, 9, 0))
                .date(LocalDateTime.of(2024, 4, 1, 19, 0))
                .strictedAge(18)
                .runningTime(LocalTime.of(2, 30))
                .place("Theater")
                .type("Concert")
                .genre("Pop")
                .isConfirmed(true)
                .build();

        ShowResponse response = ShowResponse.builder()
                .id(1L)
                .name(request.getName())
                .priceInfos(Arrays.asList(PriceInfo.of("Regular", 10000), PriceInfo.of("VIP", 20000)))
                .ticketSellers(Arrays.asList(TicketSeller.of("yes24", "https://yes24.com", "yes24-icon.png")))
                .urlId("test-show")
                .ticketOpenTime(request.getTicketOpenTime())
                .date(request.getDate())
                .strictedAge(request.getStrictedAge())
                .runningTime(request.getRunningTime())
                .place(request.getPlace())
                .type(request.getType())
                .genre(request.getGenre())
                .poster("image1.jpg")
                .detailImages(Arrays.asList("image2.jpg", "image3.jpg"))
                .isConfirmed(true)
                .build();

        when(showService.updateShow(eq(1L), any(UpdateShowRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/show/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.data.name").value("Show2"))
                .andExpect(jsonPath("$.data.priceInfos[0].type").value("Regular"))
                .andExpect(jsonPath("$.data.priceInfos[0].price").value(10000))
                .andExpect(jsonPath("$.data.priceInfos[1].type").value("VIP"))
                .andExpect(jsonPath("$.data.priceInfos[1].price").value(20000))
                .andExpect(jsonPath("$.data.ticketSellers[0].name").value("yes24"))
                .andExpect(jsonPath("$.data.ticketSellers[0].baseUrl").value("https://yes24.com"))
                .andExpect(jsonPath("$.data.ticketSellers[0].icon").value("yes24-icon.png"))
                .andExpect(jsonPath("$.data.urlId").value("test-show"))
                .andExpect(jsonPath("$.data.ticketOpenTime").value("2024-03-07T09:00:00"))
                .andExpect(jsonPath("$.data.date").value("2024-04-01T19:00:00"))
                .andExpect(jsonPath("$.data.strictedAge").value(18))
                .andExpect(jsonPath("$.data.runningTime").value("02:30:00"))
                .andExpect(jsonPath("$.data.place").value("Theater"))
                .andExpect(jsonPath("$.data.type").value("Concert"))
                .andExpect(jsonPath("$.data.genre").value("Pop"))
                .andExpect(jsonPath("$.data.poster").value("image1.jpg"))
                .andExpect(jsonPath("$.data.detailImages[0]").value("image2.jpg"))
                .andExpect(jsonPath("$.data.detailImages[1]").value("image3.jpg"))
                .andExpect(jsonPath("$.data.isConfirmed").value(true));

    }

    @Test
    void deleteShow() throws Exception {
        doNothing().when(showService).deleteShow(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/show/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
