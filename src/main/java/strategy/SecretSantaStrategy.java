package strategy;

import data.Participant;

import java.util.List;

public interface SecretSantaStrategy {
    List<String> assign(List<Participant> participants);
}
