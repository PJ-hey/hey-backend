package hey.io.heybackend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hey.io.heybackend.user.UserInfoFixture;
import hey.io.heybackend.user.dtos.request.FollowShowListRequest;
import hey.io.heybackend.user.dtos.request.FollowShowRequest;
import hey.io.heybackend.user.dtos.request.UpdateUserRequest;
import hey.io.heybackend.user.dtos.response.FollowShowResponse;
import hey.io.heybackend.user.dtos.response.UpdateUserResponse;
import hey.io.heybackend.user.dtos.response.UserResponse;
import hey.io.heybackend.user.service.UserFollowService;
import hey.io.heybackend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserFollowService userFollowService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMyInfo() throws Exception {
        UserResponse userResponse = new UserResponse(1L, "test@test.com", "test", "01000000000", "test");
        when(userService.getUser(1L)).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.userName").value("test"))
                .andExpect(jsonPath("$.phoneNumber").value("01000000000"))
                .andExpect(jsonPath("$.nickName").value("test"));
    }

    @Test
    void updateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("newNickName", "newPassword");
        UpdateUserResponse updateUserResponse = new UpdateUserResponse(1L, "newNickName");
        when(userService.updateUser(eq(1L), any(UpdateUserRequest.class))).thenReturn(updateUserResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nickName").value("newNickName"));
    }

    @Test
    public void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(eq(1l));

        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void followShow() throws Exception {

        FollowShowRequest request = UserInfoFixture.getFollowShowRequestInfo();
        FollowShowResponse response = UserInfoFixture.getFollowShowResponseInfo(request);
        when(userFollowService.followShow(any())).thenReturn(response);
        mockMvc.perform(post("/user/followed_show")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void getFollowShow() throws Exception {

        FollowShowListRequest request = UserInfoFixture.getFollowShowListRequestInfo();

        when(userFollowService.getFollowShow(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/user/followed_show")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
