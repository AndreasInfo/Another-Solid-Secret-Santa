package de.amrim.handler;

import de.amrim.data.EventContext;
import de.amrim.data.Participant;
import de.amrim.util.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.config.ConfigLoader;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class MailHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger(MailHandler.class);

    @Override
    public void handle(EventContext context) {
        String host = AppProperties.getInstance().get(ConfigLoader.Property.SMTP_HOST.key());
        int port = Integer.parseInt(AppProperties.getInstance().get(ConfigLoader.Property.SMTP_PORT.key()));
        String user = AppProperties.getInstance().get(ConfigLoader.Property.SMTP_USERNAME.key());
        String pass = AppProperties.getInstance().get(ConfigLoader.Property.SMTP_PASSWORD.key());
        LOG.debug("host={}, port={}, user={}", host, port, user);
        try (Mailer mailer = MailerBuilder.withSMTPServerHost(host)
                .withSMTPServerPort(port)
                .withSMTPServerUsername(user)
                .withSMTPServerPassword(pass)
                .withTransportStrategy(TransportStrategy.SMTPS)
                .withProperty("mail.smtps.quitwait", false)
                .buildMailer()) {
            for (Participant secretSanta : context.getSecretSantas()) {
                LOG.debug("Send mail to {} ({})", secretSanta.name(), secretSanta.mail());
                Email mail = EmailBuilder.startingBlank().from(null, user)
                        .to(secretSanta.name(), secretSanta.mail())
                        .withSubject("Neue Nachricht von Another-Solid-Secret-Santa!")
                        .withPlainText(getText(context, secretSanta))
                        .buildEmail();
                mailer.sendMail(mail);
                LOG.debug("Sent mail successfully to {} ({})", secretSanta.name(), secretSanta.mail());
            }

        } catch (Exception e) {
            LOG.warn("Could not send mail!", e);
        }
    }

    private String getText(EventContext context, Participant secretSanta) {
        Participant recipient = context.getRecipientFor(secretSanta);
        return String.format("Hohoho %s,\n", secretSanta.name())
                + "\n"
                + "du wurdest auf einen Wichtelabend eingeladen! Hier die wichtigsten Daten in Kürze:\n"
                + String.format("- besorge ein Geschenk für: %s\n", recipient.name())
                + String.format("- komme hier hin: : %s\n", context.getLocation())
                + String.format("- sei pünktlich am: %s\n", context.getDate())
                + String.format("- bezahle maximal: %s\n", context.getBudget())
                + "\n"
                + "Viel Spaß und bis bald!";
    }
}
