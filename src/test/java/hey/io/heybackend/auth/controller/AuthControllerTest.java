package hey.io.heybackend.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import hey.io.heybackend.auth.dtos.SendMailBody;
import hey.io.heybackend.auth.dtos.VerifyCodeBody;
import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.repository.AuthTokenRepository;
import hey.io.heybackend.authToken.service.AuthTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP).
            withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin")).
            withPerMethodLifecycle(false);
    private AuthTokenService authTokenService;
    @Autowired
    private AuthTokenRepository authTokenRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {

        authTokenService = new AuthTokenService(authTokenRepository);
        authTokenService.createToken("123@naver.com");
    }


    @Test
    void SendMail_Test() throws Exception {
        SendMailBody bodyDto = new SendMailBody();
        ObjectMapper mapper = new ObjectMapper();
        bodyDto.setEmail("123@naver.com");
        String body = mapper.writeValueAsString(bodyDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/email").content(body).contentType("application/json")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void VerifyCode_Test() throws Exception {
        AuthToken token = this.authTokenService.findTokenByEmail("123@naver.com");
        UUID uuid = token.getUuid();
        String code = token.getVerificationCode();
        String url = String.format("/auth/%s/verify", uuid);

        VerifyCodeBody bodyDto = new VerifyCodeBody();
        ObjectMapper mapper = new ObjectMapper();

        bodyDto.setCode(code);
        String body = mapper.writeValueAsString(bodyDto);

        this.mockMvc.perform(MockMvcRequestBuilders.post(url).content(body).contentType("application/json")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
