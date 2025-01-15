package de.amrim.strategy;

import de.amrim.data.Constraint;
import de.amrim.data.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleStrategy implements SecretSantaStrategy {
    private static final Logger LOG = LogManager.getLogger(ShuffleStrategy.class);
    private static final int MAX_TRIES = 1000;

    @Override
    public List<Participant> assign(List<Participant> secretSantas, List<Constraint> constraints) {
        if (secretSantas.size() < 2) {
            LOG.warn("Not enough participants for Secret Santa!");
            return List.of();
        }

        List<Participant> recipients = new ArrayList<>(secretSantas);
        for (int i = 0; i < MAX_TRIES; i++) {
            Collections.shuffle(recipients);
            if (validateSecretSantas(secretSantas, recipients, constraints)) {
                LOG.debug("Assigned Secret Santa after {} tries!", i + 1);
                return recipients;
            }
        }

        LOG.warn("Unable to assign Secret Santa after {} tries!", MAX_TRIES);
        return List.of();
    }

    private boolean validateSecretSantas(List<Participant> secretSantas, List<Participant> recipients, List<Constraint> constraints) {
        for (int i=0; i < recipients.size(); i++) {
            String recipient = recipients.get(i).name();
            String secretSanta = secretSantas.get(i).name();

            // secretSanta and recipient are the same
            if (recipient.equals(secretSanta)) {
                return false;
            }

            // constraint IS_SANTA_FOR
            List<Constraint> isSantaForConstraints = constraints.stream()
                    .filter(constraint -> constraint.nameA().equals(secretSanta) && constraint.type() == Constraint.Type.IS_SANTA_FOR)
                    .toList();
            if (isSantaForConstraints.stream().anyMatch(constraint -> !constraint.nameB().equals(recipient))) {
                return false;
            }

            // constraint IS_NO_SANTA_FOR
            List<Constraint> isNoSantaForConstraints = constraints.stream()
                    .filter(constraint -> constraint.nameA().equals(secretSanta) && constraint.type() == Constraint.Type.IS_NO_SANTA_FOR)
                    .toList();
            if (isNoSantaForConstraints.stream().anyMatch(constraint -> constraint.nameB().equals(recipient))) {
                return false;
            }
        }
        return true;
    }
}
