package stackCalculator.Operations;

import stackCalculator.Context;
import stackCalculator.exceptions.CommandException;
import static java.lang.Math.sqrt;

public class SqrtOperation extends CalcOperation {
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (context.getStack().empty()) {
            throw new CommandException("Stack is empty");
        }
        double a = context.getStack().pop();
        if (a < 0) {
            throw new CommandException("Cannot sqrt a negative number: your number is " + a);
        }
        a = sqrt(a);
        context.getStack().push(a);
    }
}
