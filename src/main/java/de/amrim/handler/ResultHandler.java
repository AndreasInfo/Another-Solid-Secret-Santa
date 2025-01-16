package de.amrim.handler;

import de.amrim.data.EventContext;
import de.amrim.util.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.config.ConfigLoader;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.util.stream.IntStream;

public class ResultHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger(ResultHandler.class);

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

            LOG.debug("Send result to admin ({})", user);
            Email mail = EmailBuilder.startingBlank().from("", user)
                    .to(null, user)
                    .withSubject("Neues Event von Another-Solid-Secret-Santa!")
                    .withPlainText(getText(context))
                    .buildEmail();
            mailer.sendMail(mail);
            LOG.debug("Sent result successfully to admin ({})", user);
        } catch (Exception e) {
            LOG.warn("Could not send result!", e);
        }
    }

    private String getText(EventContext context) {
        StringBuilder result = new StringBuilder();

        result.append("Hohoho admin,\n");
        result.append("\n");
        result.append("das ist dein Event:\n");
        result.append("\n");
        IntStream.range(0, context.getSecretSantas().size()).forEach(i -> {
            result.append(context.getSecretSantas().get(i).name());
            result.append(" -> ");
            result.append(context.getRecipients().get(i).name());
            result.append("\n");
        });
        result.append("\n");
        result.append("Ort:").append(context.getLocation()).append("\n");
        result.append("Datum:").append(context.getDate()).append("\n");
        result.append("Budget:").append(context.getBudget()).append("\n");
        result.append("\n");

        return result.toString();
    }
}
