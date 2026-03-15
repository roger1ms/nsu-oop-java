package lab2.Operations;
import lab2.Context;
import lab2.exceptions.CommandException;

abstract public class CalcOperation {
    public abstract void execute(Context context, String[] parsedLine) throws CommandException;
}
