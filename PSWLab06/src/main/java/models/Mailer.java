package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {
    private String username;
    private String password;
    private String fromEmail;
    private Properties properties = new Properties();
    private Session session = provideSession();

    private String subject = "Rejestracja do aplikacji";
    private String message = "Witaj w aplikacji! \n by Jakub Melkowski oraz Mateusz Majewski";

    public Mailer(String username, String password, String fromEmail){
        this.username = username;
        this.password = password;
        this.fromEmail = fromEmail;
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        setupProperties();
    }

    private void setupProperties(){
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","mail.smtp.gmail.com");
        properties.put("mail.smtp.port","587");
    }

    public void sendMail(String toEmail){
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setText(message);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",username,password);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void sendMail(String toEmail, String subject, String message){
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setText(message);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",username,password);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private Session provideSession(){
        return Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
