package de.dewarim.goblin

import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.event.TransportEvent
import javax.mail.event.TransportListener
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MyMailService {

    def grailsApplication

    static Properties mailProps = new Properties()

    SendMailResult sendMail(String sender, recipients, String subject, body) {
        if(! mailProps.containsKey('mail.host')){
            def config = grailsApplication.config.mail
            mailProps.setProperty("mail.transport.protocol", config?.protocol ?: "smtp")
            mailProps.setProperty("mail.host", config?.host ?: "localhost")
            mailProps.setProperty("mail.user", config?.user ?: "LittleGoblin")
            mailProps.setProperty("mail.password", config?.password ?: "")
        }
        log.debug("mailProps: "+mailProps.dump())
        Session mailSession = Session.getDefaultInstance(mailProps, null)
        Transport transport = mailSession.getTransport()

        MimeMessage message = new MimeMessage(mailSession)
        message.setSubject(subject)
        message.addFrom(new InternetAddress(sender))
        message.setContent(body, "text/html")
        recipients.each {recipient ->
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient))
        }
        
        transport.connect()
        def listener = new TransportListener() {
            TransportEvent event
            @Override
            void messageDelivered(TransportEvent transportEvent) {
                event = transportEvent
            }

            @Override
            void messageNotDelivered(TransportEvent transportEvent) {
                event = transportEvent
            }

            @Override
            void messagePartiallyDelivered(TransportEvent transportEvent) {
                event = transportEvent
            }
        }
        transport.addTransportListener(listener)
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO))
        Thread.sleep(3000) // sleep a moment to in case the mail server is busy.
        transport.close()
        return new SendMailResult(okay: listener.event?.type == TransportEvent.MESSAGE_DELIVERED)
    }
}
