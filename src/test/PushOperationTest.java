package test;

import lab2.Context;
import lab2.Operations.PushOperation;
import lab2.exceptions.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PushOperationTest {

    private Context context;
    private PushOperation pushOp;

    @BeforeEach
    void setUp() {
        context = new Context();
        pushOp = new PushOperation();
    }

    @Test
    void testPushNumber() throws CommandException {
        pushOp.execute(context, new String[]{"PUSH", "42.5"});
        assertEquals(1, context.getStack().size());
        assertEquals(42.5, context.getStack().peek());
    }

    @Test
    void testPushVariable() throws CommandException {
        context.getVariables().put("x", 100.0);
        pushOp.execute(context, new String[]{"PUSH", "x"});
        assertEquals(100.0, context.getStack().peek());
    }

    @Test
    void testPushUndefinedVariable() {
        CommandException ex = assertThrows(CommandException.class, () -> {
            pushOp.execute(context, new String[]{"PUSH", "unknown"});
        });
        assertTrue(ex.getMessage().contains("not defined"));
    }

    @Test
    void testPushNoArgument() {
        assertThrows(CommandException.class, () -> {
            pushOp.execute(context, new String[]{"PUSH"});
        });
    }
}