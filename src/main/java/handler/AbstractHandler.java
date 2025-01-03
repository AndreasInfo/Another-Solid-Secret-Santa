package handler;

import data.EventContext;

import java.util.Scanner;

public abstract class AbstractHandler {

    protected final Scanner scanner = new Scanner(System.in);

    /**
     * Performs the handler's logic.
     *
     * @param context event context on which the step operates
     */
    public abstract void handle(EventContext context);
}
