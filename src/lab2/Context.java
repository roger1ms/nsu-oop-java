package lab2;

import java.util.*;

public class Context {

    private Stack<Double> stack;
    Map<String, Double> variables;
    public Context() {
        stack = new Stack<>();
        variables = new HashMap<>();
    }

    public Stack<Double> getStack() {
        return stack;
    }

    public Map<String, Double> getVariables() {
        return variables;
    }
}
