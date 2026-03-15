package stackCalculator.Operations;
import stackCalculator.Context;
import stackCalculator.exceptions.CommandException;

abstract public class CalcOperation {
    public abstract void execute(Context context, String[] parsedLine) throws CommandException;
}
