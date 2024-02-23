package hey.io.heybackend.auth.controller;

import hey.io.heybackend.auth.dtos.SendMailBody;
import hey.io.heybackend.auth.dtos.VerifyCodeBody;
import hey.io.heybackend.authToken.entities.AuthToken;
import hey.io.heybackend.authToken.service.AuthTokenService;
import hey.io.heybackend.email.service.EmailService;
import org.springframework.web.bind.annotation.*;

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
    public UUID SendMail(@RequestBody SendMailBody body) {
        AuthToken authToken = authTokenService.createToken(body.getEmail());
        emailService.sendMessage(authToken.getEmail(), authToken.getVerificationCode());
        return authToken.getUuid();
    }

    @PostMapping("/auth/{uuid}/verify")
    public boolean VerifyCode(@RequestBody VerifyCodeBody body, @PathVariable String uuid) {
        UUID parsedUUID = UUID.fromString(uuid);
        boolean result = authTokenService.verifyCode(parsedUUID, body.getCode());
        return result;
    }
}
