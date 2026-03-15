package test;

import stackCalculator.Context;
import stackCalculator.Operations.DefineOperation;
import stackCalculator.exceptions.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DefineOperationTest {

    private Context context;
    private DefineOperation defineOp;

    @BeforeEach
    void setUp() {
        context = new Context();
        defineOp = new DefineOperation();
    }

    @Test
    void testDefineVariable() throws CommandException {
        defineOp.execute(context, new String[]{"DEFINE", "a", "4.0"});
        assertEquals(4.0, context.getVariables().get("a"));
    }

    @Test
    void testDefineRedefineVariable() throws CommandException {
        defineOp.execute(context, new String[]{"DEFINE", "a", "4.0"});
        defineOp.execute(context, new String[]{"DEFINE", "a", "8.0"});
        assertEquals(8.0, context.getVariables().get("a"));
    }

    @Test
    void testDefineInvalidNameIsNumber() {
        assertThrows(CommandException.class, () -> {
            defineOp.execute(context, new String[]{"DEFINE", "123", "5.0"});
        });
    }

    @Test
    void testDefineInvalidValue() {
        assertThrows(CommandException.class, () -> {
            defineOp.execute(context, new String[]{"DEFINE", "a", "notanumber"});
        });
    }

    @Test
    void testDefineNotEnoughArguments() {
        assertThrows(CommandException.class, () -> {
            defineOp.execute(context, new String[]{"DEFINE", "a"});
        });
    }
}