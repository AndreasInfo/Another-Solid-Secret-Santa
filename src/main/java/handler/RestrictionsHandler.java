package handler;

import data.EventContext;

public class RestrictionsHandler extends AbstractHandler {

    @Override
    public void handle(EventContext context) {
        if (context.getParticipants().isEmpty()) {
            throw new IllegalArgumentException("Participants must be added before adding restrictions.");
        }
        System.out.println("Restrictions added: " + context.getBudget());
    }
}
