package de.amrim;

import de.amrim.data.EventContext;
import de.amrim.handler.*;
import de.amrim.process.EventCreationChain;
import de.amrim.util.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        LOG.info("!!! Start another-solid-secret-santa !!!");
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // ensure singleton is initialized
        AppProperties.getInstance();

        EventContext context = new EventContext();

        EventCreationChain chain = new EventCreationChain();
        chain.addStep(new ParticipantsHandler());
        chain.addStep(new ConstraintsHandler());
        chain.addStep(new LocationHandler());
        chain.addStep(new DateHandler());
        chain.addStep(new BudgetHandler());
        chain.addStep(new MailHandler());

        chain.execute(context);

        System.out.println(context);
    }
}
