package stackCalculator;
import stackCalculator.Operations.CalcOperation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

public class OperationFactory {
    private Map<String, String> operationMap;
    private static final Logger logger = Logger.getLogger(OperationFactory.class.getName());

    public OperationFactory() {
        logger.fine("Initializing OperationFactory...");
        operationMap = new HashMap<>();
        try (InputStream input = getClass().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.severe("Cannot find config.txt file");
                throw new RuntimeException("Cannot find config.txt file");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length < 2) {
                    logger.warning("Invalid config line: " + line);
                    continue;
                }
                operationMap.put(parts[0], parts[1]);
            }
            logger.info("Loaded " + operationMap.size() + " commands");
        }
        catch(Exception e) {
            logger.severe("Failed to load factory config: " + e.getMessage());
            throw new RuntimeException("Failed to load factory config", e);
        }

    }

    public CalcOperation getOperation(String command) {
        logger.fine("Requesting operation: " + command);

        String operationName = operationMap.get(command);
        if (operationName == null) {
            logger.severe("Unknown command: " + command);
            throw new IllegalArgumentException("No such operation: " + command);
        }
        try {
            logger.fine("Loading class: " + operationName);
            Class<?> operationClass = Class.forName(operationName);
            logger.fine("Operation created successfully: " + command);
            return (CalcOperation) operationClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            logger.severe("Failed to create operation '" + command + "': " + e.getMessage());
            throw new RuntimeException("Failed to create operation: " + command, e);
        }
    }
}
