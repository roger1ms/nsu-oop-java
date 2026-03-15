package test;

import lab2.OperationFactory;
import lab2.Operations.CalcOperation;
import lab2.Operations.PushOperation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperationFactoryTest {

    @Test
    void testCreateKnownOperation() {
        OperationFactory factory = new OperationFactory();
        CalcOperation op = factory.getOperation("PUSH");
        assertNotNull(op);
        assertTrue(op instanceof PushOperation);
    }

    @Test
    void testCreateUnknownOperation() {
        OperationFactory factory = new OperationFactory();
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> factory.getOperation("UNKNOWN_CMD")
        );
        assertTrue(ex.getMessage().contains("No such operation"));
    }
}