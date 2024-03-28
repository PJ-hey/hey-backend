package hey.io.heybackend.artist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.heybackend.artist.ArtistInfoFixture;
import hey.io.heybackend.artist.dtos.request.CreateArtistRequest;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.artist.service.ArtistService;
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
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createArtist() throws Exception {

        CreateArtistRequest request = ArtistInfoFixture.getCreateRequestInfo();
        ArtistResponse response = ArtistInfoFixture.getCreateArtistResponseInfo(request);
        when(artistService.createArtist(any())).thenReturn(response);
        mockMvc.perform(post("/artist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    void getArtistInfo() throws Exception {

        CreateArtistRequest request = ArtistInfoFixture.getCreateRequestInfo();
        ArtistResponse response = ArtistInfoFixture.getCreateArtistResponseInfo(request);

        when(artistService.getArtistInfo(any())).thenReturn(response);
        mockMvc.perform(get("/artist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void updateArtist() throws Exception {

        UpdateArtistRequest request = ArtistInfoFixture.getUpdateRequestInfo();
        ArtistResponse response = ArtistInfoFixture.getUpdateArtistResponseInfo(request);
        when(artistService.updateArtist(eq(1l), any())).thenReturn(response);
        mockMvc.perform(put("/artist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void deleteArtist() throws Exception {

        doNothing().when(artistService).deleteArtist(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void getAlbum() throws Exception {

        when(artistService.getAlbums(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/artist/1/album")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void getArtistShow() throws Exception {

        when(artistService.getArtistShow(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/artist/1/show")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
