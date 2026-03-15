package lab2.Operations;

import lab2.Context;
import lab2.exceptions.CommandException;

public class PopOperation extends CalcOperation {
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (parsedLine == null || parsedLine.length != 1) {
            throw new CommandException("POP need only 1 arguments");
        }
        if (context.getStack().empty()) {
            throw new CommandException("Stack is empty");
        }
        context.getStack().pop();
    }
}
