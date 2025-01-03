package strategy;

import data.Constraint;
import data.Participant;

import java.util.List;

public interface SecretSantaStrategy {
    List<Participant> assign(List<Participant> participants, List<Constraint> constraints);
}
