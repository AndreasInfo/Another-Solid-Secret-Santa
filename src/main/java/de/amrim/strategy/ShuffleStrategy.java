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

    public List<Participant> assign(List<Participant> participants, List<Constraint> constraints) {
        if (participants.size() < 2) {
            LOG.warn("Not enough participants for Secret Santa!");
            return List.of();
        }

        List<Participant> secretSantas = new ArrayList<>(participants);
        for (int i = 0; i < MAX_TRIES; i++) {
            Collections.shuffle(secretSantas);
            if (validateSecretSantas(participants, secretSantas, constraints)) {
                LOG.debug("Assigned Secret Santa after {} tries!", i + 1);
                return secretSantas;
            }
        }

        LOG.warn("Unable to assign Secret Santa after {} tries!", MAX_TRIES);
        return List.of();
    }

    private boolean validateSecretSantas(List<Participant> participants, List<Participant> secretSantas, List<Constraint> constraints) {
        for (int i=0; i < secretSantas.size(); i++) {
            String santa = secretSantas.get(i).name();
            String participant = participants.get(i).name();

            // secretSanta and participant are the same
            if (santa.equals(participant)) {
                return false;
            }

            // constraint IS_SANTA_FOR
            List<Constraint> isSantaForConstraints = constraints.stream()
                    .filter(constraint -> constraint.nameA().equals(santa) && constraint.type() == Constraint.Type.IS_SANTA_FOR)
                    .toList();
            if (isSantaForConstraints.stream().anyMatch(constraint -> !constraint.nameB().equals(participant))) {
                return false;
            }

            // constraint IS_NO_SANTA_FOR
            List<Constraint> isNoSantaForConstraints = constraints.stream()
                    .filter(constraint -> constraint.nameA().equals(santa) && constraint.type() == Constraint.Type.IS_NO_SANTA_FOR)
                    .toList();
            if (isNoSantaForConstraints.stream().anyMatch(constraint -> constraint.nameB().equals(participant))) {
                return false;
            }
        }
        return true;
    }
}
