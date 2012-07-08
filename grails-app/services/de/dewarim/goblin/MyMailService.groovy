package de.dewarim.goblin

import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Message

class MyMailService {

    def grailsApplication
    
    static Properties mailProps = new Properties()
    
    def sendMail(sender, recipients, subject, body) {
        if(! mailProps.containsKey('mail.host')){
            def config = grailsApplication.config.mail
            mailProps.setProperty("mail.transport.protocol", config?.protocol ?: "smtp");
            mailProps.setProperty("mail.host", config?.host ?: "localhost");
            mailProps.setProperty("mail.user", config?.user ?: "LittleGoblin");
            mailProps.setProperty("mail.password", config?.password ?: "");
        }
       
        Session mailSession = Session.getDefaultInstance(mailProps, null);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.addFrom(new InternetAddress(sender))
        message.setText(body, "UTF-8");
        recipients.each {recipient ->
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient))
        }

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
