package test;

import stackCalculator.Context;
import stackCalculator.Operations.PrintOperation;
import stackCalculator.exceptions.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrintOperationTest {

    private Context context;
    private PrintOperation printOp;

    @BeforeEach
    void setUp() {
        context = new Context();
        printOp = new PrintOperation();
    }

    @Test
    void testPrintSuccess() throws CommandException {
        context.getStack().push(42.0);
        printOp.execute(context, new String[]{"PRINT"});
        assertEquals(1, context.getStack().size()); // Элемент не удаляется
        assertEquals(42.0, context.getStack().peek());
    }

    @Test
    void testPrintEmptyStack() {
        assertThrows(CommandException.class, () -> {
            printOp.execute(context, new String[]{"PRINT"});
        });
    }

    @Test
    void testPrintWithExtraArguments() {
        context.getStack().push(10.0);
        assertThrows(CommandException.class, () -> {
            printOp.execute(context, new String[]{"PRINT", "5"});
        });
    }
}