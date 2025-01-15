package de.amrim.data;

import de.amrim.strategy.SecretSantaStrategy;
import de.amrim.strategy.ShuffleStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EventContext {
    private static final Logger LOG = LogManager.getLogger(EventContext.class);

    private final List<Participant> secretSantas = new ArrayList<>();
    private final List<Constraint> constraints = new ArrayList<>();
    private String location;
    private String date;
    private Double budget;

    private final SecretSantaStrategy strategy = new ShuffleStrategy();
    private List<Participant> recipients = new ArrayList<>();

    public EventContext() {
    }

    public List<Participant> getSecretSantas() {
        return new ArrayList<>(secretSantas);
    }

    public void addSecretSanta(Participant participant) {
        secretSantas.add(participant);
        setRecipients(strategy.assign(getSecretSantas(), getConstraints()));
    }

    public List<Constraint> getConstraints() {
        return new ArrayList<>(constraints);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
        setRecipients(strategy.assign(getSecretSantas(), getConstraints()));

        if (hasNoResult()) {
            System.out.println("CAUTION: Removed last constraint, because could not assign Secret Santas!");
            constraints.removeLast();
            setRecipients(strategy.assign(getSecretSantas(), getConstraints()));
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

    public List<Participant> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Participant> recipients) {
        this.recipients = recipients;
    }

    public boolean hasNoResult() {
        return getRecipients().isEmpty();
    }

    public Participant getRecipientFor(Participant participant) {
        if (hasNoResult()) {
            LOG.warn("No result present yet!");
            return null;
        }

        return IntStream.range(0, getSecretSantas().size())
                .filter(i -> getSecretSantas().get(i).equals(participant))
                .mapToObj(getRecipients()::get)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Something went terribly wrong"));
    }

    @Override
    public String toString() {
        return String.format(
                "EventContext(secretSantas=%s, recipients=%s, constraints=%s, location=%s, date=%s, budget=%s)",
                secretSantas, recipients, constraints, location, date, budget
        );
    }
}
