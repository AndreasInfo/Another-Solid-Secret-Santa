package process;

import data.EventContext;
import handler.AbstractHandler;

import java.util.ArrayList;
import java.util.List;

public class EventCreationChain {
    private final List<AbstractHandler> steps;

    public EventCreationChain() {
        this.steps = new ArrayList<>();
    }

    public void addStep(AbstractHandler step) {
        steps.add(step);
    }

    public void execute(EventContext context) {
        for (AbstractHandler step : steps) {
            step.handle(context);
        }
    }
}
