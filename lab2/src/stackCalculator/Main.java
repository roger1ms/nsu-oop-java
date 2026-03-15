package stackCalculator;

import stackCalculator.Operations.CalcOperation;
import stackCalculator.exceptions.CommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;

import static stackCalculator.Parcer.parce;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        Logger rootLogger = Logger.getLogger("");

        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        FileHandler fh = new FileHandler("logger.log", true);
        fh.setFormatter(new SimpleFormatter());
        fh.setLevel(Level.ALL);

        rootLogger.addHandler(fh);
        rootLogger.setLevel(Level.ALL);

        logger.info("Calculator started");
        logger.info("Arguments count: " + args.length);
        Scanner input = null;
        if (args.length > 0) {
            try {
                String filename = args[0];
                input = new Scanner(new File(filename));
            }
            catch (FileNotFoundException e) {
                logger.severe("File not found: " + args[0]);
            }
        }
        else {
            input = new Scanner(System.in);
        }

        Context context =  new Context();
        OperationFactory factory = new OperationFactory();

        logger.fine("context and factory initialized");

        int commandCount = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            commandCount++;
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }

            String[] tokens = parce(line);
            String operationName = tokens[0];
            logger.fine("Command: " + operationName);

            CalcOperation operation;
            try {
                operation = factory.getOperation(operationName);
                logger.fine("Operation created: " + operationName);
            }
            catch (IllegalArgumentException e) {
                logger.severe("Unknown command '" + operationName + "' at line " + commandCount);
                continue;
            }

            try {
                operation.execute(context, tokens);
                logger.info("Operation executed: " + operationName);
                logger.fine("Stack state: " + context.getStack());
            }
            catch (CommandException e) {
                logger.severe("Execution error at line " + commandCount + ": " + e.getMessage());
            }
        }
        input.close();
        logger.info("Calculator finished");
        logger.info("Total lines processed: " + commandCount);
    }
}