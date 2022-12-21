package logic.utils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.util.Properties;

public class Email {
    public static void sendMail(String file, String email){
        try {
            Properties props = new Properties();

            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port","587");
            props.setProperty("mail.smtp.user", "garciaj1246@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            BodyPart testroot = new MimeBodyPart();
            testroot.setText("Hola "+email+", a continuación puede descargar su frase comprimida.");
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(file)));
            adjunto.setFileName(file);
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(testroot);
            multiParte.addBodyPart(adjunto);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("garciaj1246@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Huffman Decoding");
            message.setContent(multiParte);

            Transport t = session.getTransport("smtp");
            t.connect("garciaj1246@gmail.com", "wrfcocekpkcxhqvq");
            t.sendMessage(message, message.getAllRecipients());
            t.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
