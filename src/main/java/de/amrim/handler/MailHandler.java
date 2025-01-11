package de.amrim.handler;

import de.amrim.data.EventContext;
import de.amrim.data.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.config.ConfigLoader;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import de.amrim.util.AppProperties;

import java.util.List;

public class MailHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void handle(EventContext context) {
        String host = AppProperties.INSTANCE.get(ConfigLoader.Property.SMTP_HOST.key());
        int port = Integer.parseInt(AppProperties.INSTANCE.get(ConfigLoader.Property.SMTP_PORT.key()));
        String user = AppProperties.INSTANCE.get(ConfigLoader.Property.SMTP_USERNAME.key());
        String pass = AppProperties.INSTANCE.get(ConfigLoader.Property.SMTP_PASSWORD.key());
        LOG.debug("host={}, port={}, user={}", host, port, user);
        try (Mailer mailer = MailerBuilder.withSMTPServerHost(host)
                .withSMTPServerPort(port)
                .withSMTPServerUsername(user)
                .withSMTPServerPassword(pass)
                .withTransportStrategy(TransportStrategy.SMTPS)
                .withProperty("mail.smtps.quitwait", false)
                .buildMailer()) {
            List<Participant> participants = context.getParticipants();
            for (Participant participant : participants) {
                LOG.debug("Send mail to {} ({})", participant.name(), participant.mail());
                Email mail = EmailBuilder.startingBlank().from("blabla", user)
                        .to(participant.name(), participant.mail())
                        .withSubject("Neue Nachricht von Another-Solid-Secret-Santa!")
                        .withPlainText("We should meet up! ;)")
                        .buildEmail();
                mailer.sendMail(mail);
                LOG.debug("Sent mail successfully to {} ({})", participant.name(), participant.mail());
            }

        } catch (Exception e) {
            LOG.warn("Could not send mail!", e);
        }
    }
}
