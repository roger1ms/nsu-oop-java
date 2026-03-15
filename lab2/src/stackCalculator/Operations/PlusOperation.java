package stackCalculator.Operations;

import stackCalculator.Context;
import stackCalculator.exceptions.CommandException;

public class PlusOperation extends CalcOperation {
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (context.getStack().empty()) {
            throw new CommandException("Not enough arguments for operation: need 2 more arguments");
        }
        double val1 = context.getStack().pop();
        if (context.getStack().empty()) {
            throw new CommandException("Not enough arguments for operation: need 1 more arguments");
        }
        double val2 = context.getStack().pop();
        context.getStack().push(val1 + val2);
    }
}
