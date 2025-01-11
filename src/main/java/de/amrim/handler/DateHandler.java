package de.amrim.handler;

import de.amrim.data.EventContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHandler extends AbstractHandler {
    private static final Logger LOG = LogManager.getLogger();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void handle(EventContext context) {
        while (true) {
            System.out.println("Add date (DD.MM.YYYY): ");
            String userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Please provide a date!");
                continue;
            }

            try {
                LocalDate.parse(userInput, DATE_FORMAT);
                context.setDate(userInput);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date!");
            }
        }

        LOG.info("Date added: {}", context.getDate());
    }
}
