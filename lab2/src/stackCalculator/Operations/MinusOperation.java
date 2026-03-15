package stackCalculator.Operations;

import stackCalculator.Context;
import stackCalculator.exceptions.CommandException;

public class MinusOperation extends CalcOperation {
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        double val1 = context.getStack().pop();
        if (context.getStack().empty()) {
            throw new CommandException("Not enough arguments for operation");
        }
        double val2 = context.getStack().pop();
        context.getStack().push(val1 - val2);
    }
}
