package apiUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Properties;

public class MailerAPI {
    private static String code;
    public void MailerApi() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getCode() {
        return code;
    }
    public static String generateRandomCode() {
        // Generate a random 5-character code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomCode = new StringBuilder(5);
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            randomCode.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return randomCode.toString();
    }
    public static void sendMail(String email, String pwd, String recepient) {
        String randomCode = generateRandomCode();
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.office365.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, pwd);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recepient)
            );
            message.setSubject("Password recovery");
            message.setText("Here is your code: "+randomCode);
            MailerAPI mailerAPI = new MailerAPI();
            mailerAPI.setCode(randomCode);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur mailer: " + e.getMessage());
        }
    }
}
