package stackCalculator.Operations;

import stackCalculator.Context;
import stackCalculator.exceptions.CommandException;

public class DefineOperation extends CalcOperation{
    @Override
    public void execute(Context context, String[] parsedLine) throws CommandException {
        if (parsedLine == null || parsedLine.length != 3) {
            throw new CommandException("DEFINE need 3 arguments");
        }
        String var =  parsedLine[1];
        boolean hasLetter = false;
        for (char c : var.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                break;
            }
        }

        if (!hasLetter || !Character.isLetter(var.charAt(0))) {
            throw new CommandException("Var name must contain at least one letter " +
                    "and dont start with a number: " + var);
        }


        double a = 0.0;
        try {
            a = Double.parseDouble(parsedLine[2]);
        }
        catch (Exception e) {
            throw new CommandException("Second argument is not a number: your argument is " + parsedLine[2]);
        }

        context.getVariables().put(var, a);
    }
}
