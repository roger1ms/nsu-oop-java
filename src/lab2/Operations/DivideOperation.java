package lab2.Operations;

import lab2.Context;
import lab2.exceptions.CommandException;

public class DivideOperation extends CalcOperation {
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        double val1 = context.getStack().pop();
        if (context.getStack().empty()) {
            throw new CommandException("Not enough arguments for operation");
        }
        double val2 = context.getStack().pop();
        if (val2 == 0.0) {
            throw new CommandException("Cannot divide by zero");
        }
        context.getStack().push(val1 / val2);
    }
}
