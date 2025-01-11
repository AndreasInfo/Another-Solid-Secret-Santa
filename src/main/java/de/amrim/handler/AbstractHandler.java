package de.amrim.handler;

import de.amrim.data.EventContext;

import java.util.Scanner;

public abstract class AbstractHandler {

    protected final Scanner scanner = new Scanner(System.in);

    /**
     * Performs the de.handy_solutions.handler's logic.
     *
     * @param context event context on which the step operates
     */
    public abstract void handle(EventContext context);
}
