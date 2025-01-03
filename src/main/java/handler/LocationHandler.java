package handler;

import data.EventContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocationHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void handle(EventContext context) {
        while (true) {
            System.out.println("Add location: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Please provide a location!");
            } else {
                context.setLocation(userInput);
                break;
            }
        }

        LOG.info("Location added: {}", context.getLocation());
    }
}
