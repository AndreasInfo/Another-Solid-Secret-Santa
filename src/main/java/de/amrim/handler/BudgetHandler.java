package de.amrim.handler;

import de.amrim.data.EventContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BudgetHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger(BudgetHandler.class);

    @Override
    public void handle(EventContext context) {
        while (true) {
            System.out.println("Add budget: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Please provide a budget!");
                continue;
            }

            try {
                double budget = Double.parseDouble(userInput);
                context.setBudget(budget);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid budget!");
            }
        }

        LOG.info("Budget added: {}", context.getBudget());
    }
}
