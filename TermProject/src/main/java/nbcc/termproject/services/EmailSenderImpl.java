package nbcc.termproject.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender{

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String subject, String text, String from, String to) {

        var message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);


    }
}
