package test;

import lab2.Context;
import lab2.Operations.PopOperation;
import lab2.exceptions.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class PopOperationTest {

    private Context context;
    private PopOperation popOp;

    @BeforeEach
    void setUp() {
        context = new Context();
        popOp = new PopOperation();
    }

    @Test
    @DisplayName("POP: успешное удаление элемента")
    void testPopSuccess() throws CommandException {
        context.getStack().push(42.0);
        assertEquals(1, context.getStack().size());

        popOp.execute(context, new String[]{"POP"});

        assertEquals(0, context.getStack().size());
    }

    @Test
    @DisplayName("POP: пустой стек — ошибка")
    void testPopEmptyStack() {
        CommandException ex = assertThrows(CommandException.class, () -> {
            popOp.execute(context, new String[]{"POP"});
        });
        assertTrue(ex.getMessage().contains("empty"));
    }

    @Test
    @DisplayName("POP: лишние аргументы — ошибка")
    void testPopWithExtraArguments() {
        context.getStack().push(10.0);

        CommandException ex = assertThrows(CommandException.class, () -> {
            popOp.execute(context, new String[]{"POP", "5"});
        });
        assertTrue(ex.getMessage().contains("argument"));
    }

    @Test
    @DisplayName("POP: несколько элементов")
    void testPopMultipleElements() throws CommandException {
        context.getStack().push(1.0);
        context.getStack().push(2.0);
        context.getStack().push(3.0);

        popOp.execute(context, new String[]{"POP"});

        assertEquals(2, context.getStack().size());
        assertEquals(2.0, context.getStack().peek());
    }
}