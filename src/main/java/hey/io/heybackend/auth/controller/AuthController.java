package hey.io.heybackend.auth.controller;

import hey.io.heybackend.auth.dtos.SendMailBody;
import hey.io.heybackend.auth.dtos.VerifyCodeBody;
import hey.io.heybackend.authToken.dtos.VerifyCodeDTO;
import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.service.AuthTokenService;
import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.email.dtos.SendMessageDTO;
import hey.io.heybackend.email.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthController {

    private final EmailService emailService;
    private final AuthTokenService authTokenService;

    public AuthController(EmailService emailService, AuthTokenService authTokenService) {
        this.emailService = emailService;
        this.authTokenService = authTokenService;
    }

    @GetMapping("/auth/email")
    public String GetMail() {
        return "Hello";
    }

    @PostMapping("/auth/email")
    public ResponseEntity<ResponseDTO<AuthToken>> SendMail(@RequestBody SendMailBody body) {
        AuthToken authToken = authTokenService.createToken(body.getEmail());
        SendMessageDTO dto = new SendMessageDTO(body.getEmail(), authToken.getVerificationCode());
        emailService.sendMessage(dto);
        ResponseDTO<AuthToken> responseDTO = new ResponseDTO<>(true, Optional.of(authToken));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/auth/{uuid}/verify")
    public ResponseEntity<ResponseDTO<Boolean>> VerifyCode(@RequestBody VerifyCodeBody body, @PathVariable String uuid) {
        UUID parsedUUID = UUID.fromString(uuid);
        VerifyCodeDTO dto = new VerifyCodeDTO(parsedUUID, body.getCode());
        ResponseDTO<Boolean> responseDTO = new ResponseDTO<>(true, Optional.empty());

        authTokenService.verifyCode(dto);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
