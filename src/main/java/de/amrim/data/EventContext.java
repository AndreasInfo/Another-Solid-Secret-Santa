package de.amrim.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.amrim.strategy.ShuffleStrategy;
import de.amrim.strategy.SecretSantaStrategy;

import java.util.ArrayList;
import java.util.List;

public class EventContext {
    private static final Logger LOG = LogManager.getLogger();

    private final List<Participant> participants = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();
    private String location;
    private String date;
    private Double budget;

    private final SecretSantaStrategy strategy = new ShuffleStrategy();
    private List<Participant> secretSantas = new ArrayList<>();

    public EventContext() {
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
        setSecretSantas(strategy.assign(getParticipants(), getConstraints()));
    }

    public List<Constraint> getConstraints() {
        return new ArrayList<>(constraints);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
        setSecretSantas(strategy.assign(getParticipants(), getConstraints()));

        if (isEmptySecretSantas()) {
            System.out.println("CAUTION: Removed last constraint, because could not assign Secret Santas!");
            constraints.removeLast();
            setSecretSantas(strategy.assign(getParticipants(), getConstraints()));
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<Participant> getSecretSantas() {
        return secretSantas;
    }

    public void setSecretSantas(List<Participant> secretSantas) {
        this.secretSantas = secretSantas;
    }

    public boolean isEmptySecretSantas() {
        return getSecretSantas().isEmpty();
    }

    public void printResult() {
        if (isEmptySecretSantas()) {
            LOG.warn("No result present yet!");
            return;
        }

        for (int i=0; i < getParticipants().size(); i++) {
            System.out.println(getSecretSantas().get(i) + " --> " + getParticipants().get(i));
        }
        System.out.println("Location:" + getLocation());
        System.out.println("Date:" + getDate());
        System.out.println("Budget:" + getBudget());
    }

    @Override
    public String toString() {
        return String.format(
                "EventContext(date=%s, location=%s, participants=%s, budget=%s)",
                date, location, participants, budget
        );
    }
}
