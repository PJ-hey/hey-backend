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

    public void sendMessage(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@naver.com");
        message.setTo(to);
        message.setSubject("Hello this is test");
        message.setText("Hi");
        emailSender.send(message);
    }
}
