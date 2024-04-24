package nbcc.termproject.services;

public interface EmailSender {
    void send(String subject, String text, String from, String to);
}
