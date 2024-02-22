package hey.io.heybackend.email.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@naver.com");
        message.setTo(to);
        message.setSubject("Hey Service Verification code");
        message.setText(String.format("Your verification code is : %s", code));
        emailSender.send(message);
    }
}
