package de.amrim.strategy;

import de.amrim.data.Constraint;
import de.amrim.data.Participant;

import java.util.List;

public interface SecretSantaStrategy {
    List<Participant> assign(List<Participant> participants, List<Constraint> constraints);
}
