package test;

import stackCalculator.Context;
import stackCalculator.Operations.PlusOperation;
import stackCalculator.exceptions.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddOperationTest {

    private Context context;
    private PlusOperation addOp;

    @BeforeEach
    void setUp() {
        context = new Context();
        addOp = new PlusOperation();
    }

    @Test
    void testAdd() throws CommandException {
        context.getStack().push(3.0);
        context.getStack().push(7.0);
        addOp.execute(context, new String[]{});
        assertEquals(1, context.getStack().size());
        assertEquals(10.0, context.getStack().pop());
    }

    @Test
    void testAddNotEnoughOperands() {
        context.getStack().push(1.0);
        assertThrows(CommandException.class, () -> {
            addOp.execute(context, new String[]{});
        });
    }

    @Test
    void testAddEmptyStack() {
        assertThrows(CommandException.class, () -> {
            addOp.execute(context, new String[]{});
        });
    }
}