import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Main {

    public static void main(String[] args)  {
        try {
            Properties props = new Properties();

            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port","587");
            props.setProperty("mail.smtp.user", "garciaj1246@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            BodyPart testroot = new MimeBodyPart();
            testroot.setText("Texto del mensaje");
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource("F:\\Informatica\\Segundo\\ED\\ED_PROJECT/correo.txt")));
            adjunto.setFileName("correo.txt");
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(testroot);
            multiParte.addBodyPart(adjunto);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("garciaj1246@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("javiergarca923@yahoo.com"));
            message.setSubject("Hola");
            message.setContent(multiParte);

            Transport t = session.getTransport("smtp");
            t.connect("garciaj1246@gmail.com", "wrfcocekpkcxhqvq");
            t.sendMessage(message, message.getAllRecipients());
            t.close();

        }catch (Exception e){
            e.printStackTrace();
            }
    }
}