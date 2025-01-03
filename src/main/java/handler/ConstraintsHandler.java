package handler;

import data.Constraint;
import data.EventContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConstraintsHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void handle(EventContext context) {
        if (context.getParticipants().isEmpty()) {
            throw new IllegalArgumentException("Participants must be added before adding constraints!");
        }

        while (true) {
            System.out.println("Add constraint? (yes/no): ");
            String userInput = scanner.nextLine().trim();

            if ("yes".equalsIgnoreCase(userInput) || "y".equalsIgnoreCase(userInput)) {
                addConstraint(context);
            } else if ("no".equalsIgnoreCase(userInput) || "n".equalsIgnoreCase(userInput)) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }

        LOG.debug("Constraints added: {}", context.getConstraints());
    }

    private void addConstraint(EventContext context) {
        System.out.println("Add constraint");

        String nameA = askForName(context);
        Constraint.Type type = askForType();
        String nameB = askForName(context, nameA);

        context.addConstraint(new Constraint(type, nameA, nameB));
    }

    private Constraint.Type askForType() {
        while (true) {
            System.out.println("Type (1=" + Constraint.Type.IS_SANTA_FOR + ", 2=" + Constraint.Type.IS_NO_SANTA_FOR + "): ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("1")) {
                return Constraint.Type.IS_SANTA_FOR;
            } else if (userInput.equals("2")) {
                return Constraint.Type.IS_NO_SANTA_FOR;
            } else {
                System.out.println("Invalid input!");
            }
        }
    }

    private String askForName(EventContext context) {
        return askForName(context, "");
    }

    private String askForName(EventContext context, String exclude) {
        while (true) {
            System.out.println("Who:");
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Please provide a name!");
            } else if (context.getParticipants().stream().noneMatch(participant -> participant.name().equals(userInput))) {
                System.out.println("Name does not exist!");
            } else if (StringUtils.isNotBlank(exclude) && exclude.equals(userInput)) {
                System.out.println("Same name chosen!");
            } else {
                return userInput;
            }
        }
    }
}
