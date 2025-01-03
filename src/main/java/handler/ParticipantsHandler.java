package handler;

import data.EventContext;
import data.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParticipantsHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void handle(EventContext context) {
        // Add minimum participants
        while (context.getSecretSantas().isEmpty()) {
            addParticipant(context);
        }

        // Add more participants
        while (true) {
            System.out.println("Add another participant? (yes/no): ");
            String userInput = scanner.nextLine().trim();

            if ("yes".equalsIgnoreCase(userInput) || "y".equalsIgnoreCase(userInput)) {
                addParticipant(context);
            } else if ("no".equalsIgnoreCase(userInput) || "n".equalsIgnoreCase(userInput)) {
                break;
            } else {
                System.out.println("Invalid input!");
            }
        }

        LOG.debug("Participants added: {}", context.getParticipants());
    }

    private void addParticipant(EventContext context) {
        System.out.println("Create participant");

        String name = askForName(context);
        String mail = askForMail();

        context.addParticipant(new Participant(name, mail));
    }


    private String askForName(EventContext context) {
        while (true) {
            System.out.println("Add name: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Please provide a name!");
            } else if (context.getParticipants().stream().anyMatch(participant -> participant.name().equals(userInput))) {
                System.out.println("Name already exists!");
            } else {
                return userInput;
            }
        }
    }

    private String askForMail() {
        while (true) {
            System.out.println("Add mail: ");
            String userInput = scanner.nextLine().trim();

            String emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
            if (!userInput.matches(emailRegex)) {
                System.out.println("Invalid mail!");
            } else {
                return userInput;
            }
        }
    }
}
