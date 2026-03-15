package lab2.Operations;

import lab2.Context;
import lab2.exceptions.CommandException;

public class PushOperation extends CalcOperation {

    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (parsedLine == null || parsedLine.length != 2) {
            throw new CommandException("PUSH need only 2 arguments");
        }
        String dig = parsedLine[1];
        Double a;
        try {
            a = Double.parseDouble(dig);
        } catch (NumberFormatException e) {
            if (context.getVariables().containsKey(dig)) {
                a = context.getVariables().get(dig);
            } else {
                throw new CommandException("Variable '" + dig + "' not defined");
            }
        }

        context.getStack().push(a);
    }
}