package hey.io.heybackend.auth.controller;

import hey.io.heybackend.auth.dtos.sendMailBody;
import hey.io.heybackend.email.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final EmailService emailService;

    public AuthController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/auth/email")
    public String GetMail() {
        return "Hello";
    }

    @PostMapping("/auth/email")
    public void SendMail(@RequestBody sendMailBody body) {
        emailService.sendMessage(body.getEmail());
    }
}
