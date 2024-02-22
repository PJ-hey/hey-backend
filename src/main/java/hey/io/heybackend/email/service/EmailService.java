package hey.io.heybackend.email.service;

import hey.io.heybackend.email.dtos.SendMessageDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage(SendMessageDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@naver.com");
        message.setTo(dto.getTo());
        message.setSubject("Hey Service Verification code");
        message.setText(String.format("Your verification code is : %s", dto.getCode()));
        emailSender.send(message);
    }
}
