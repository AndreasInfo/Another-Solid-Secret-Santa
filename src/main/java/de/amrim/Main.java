package de.amrim;

import de.amrim.data.EventContext;
import de.amrim.handler.MailHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.amrim.process.EventCreationChain;


public class Main {

    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        LOG.info("!!! Start another-solid-secret-santa !!!");
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        EventContext context = new EventContext();

        EventCreationChain chain = new EventCreationChain();
//        chain.addStep(new ParticipantsHandler());
//        chain.addStep(new ConstraintsHandler());
//        chain.addStep(new LocationHandler());
//        chain.addStep(new DateHandler());
//        chain.addStep(new BudgetHandler());
        chain.addStep(new MailHandler());

        chain.execute(context);

        context.printResult();
    }
}
