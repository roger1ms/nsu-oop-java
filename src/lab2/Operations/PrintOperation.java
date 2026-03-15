package lab2.Operations;

import lab2.Context;
import lab2.exceptions.CommandException;

import java.util.Stack;

public class PrintOperation extends CalcOperation{
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (parsedLine == null || parsedLine.length != 1) {
            throw new CommandException("PRINT need only 1 arguments");
        }
        Stack<Double> stack = context.getStack();
        if (context.getStack().empty()) {
            throw new CommandException("Stack is empty");
        }

        double value = stack.peek();
        System.out.print(value);
    }
}
