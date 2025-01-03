package strategy;

import data.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShuffleStrategy implements SecretSantaStrategy {
    private static final Logger LOG = LogManager.getLogger();
    private static final int MAX_TRIES = 100;

    public List<String> assign(List<Participant> participants) {
        if (participants.size() < 2) {
            LOG.warn("Not enough participants for Secret Santa!");
            return List.of();
        }

        List<String> secretSantas = participants.stream()
                .map(Participant::name)
                .collect(Collectors.toList());

        for (int i = 0; i < MAX_TRIES; i++) {
            Collections.shuffle(secretSantas);
            // search for self-santas
            if (IntStream.range(0, participants.size()).noneMatch(j -> participants.get(j).name().equals(secretSantas.get(j)))) {
                LOG.debug("Assigned Secret Santa after {} tries!", i + 1);
                return secretSantas;
            }
        }

        LOG.warn("Unable to assign Secret Santa after {} tries!", MAX_TRIES);
        return List.of();
    }
}
