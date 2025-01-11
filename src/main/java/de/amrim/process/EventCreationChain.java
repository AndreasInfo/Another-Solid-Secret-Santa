package de.amrim.process;

import de.amrim.data.EventContext;
import de.amrim.handler.AbstractHandler;

import java.util.ArrayList;
import java.util.List;

public class EventCreationChain {
    private final List<AbstractHandler> steps = new ArrayList<>();

    public void addStep(AbstractHandler step) {
        steps.add(step);
    }

    public void execute(EventContext context) {
        for (AbstractHandler step : steps) {
            step.handle(context);
        }
    }
}
