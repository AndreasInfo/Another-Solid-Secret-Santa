import handler.ParticipantsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import data.EventContext;
import process.EventCreationChain;


public class Main {

    private static final Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        LOG.info("!!! Start another-solid-secret-santa !!!");
        LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        EventContext context = new EventContext();

        EventCreationChain chain = new EventCreationChain();
        chain.addStep(new ParticipantsHandler());
//        chain.addStep(new AddRestrictionStep());
//        chain.addStep(new LocationHandler());
//        chain.addStep(new DateHandler());
//        chain.addStep(new BudgetHandler());

        chain.execute(context);

        context.printResult();
    }
}
