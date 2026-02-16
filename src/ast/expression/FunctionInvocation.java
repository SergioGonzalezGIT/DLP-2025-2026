package ast.expression;

import java.util.List;

public class FunctionInvocation implements Expression {

    private int line;
    private int column;
    private Variable variable;
    private List<Expression> arguments;

    public FunctionInvocation(int line, int column, Variable variable, List<Expression> arguments) {
        this.line = line;
        this.column = column;
        this.variable = variable;
        this.arguments = arguments;
    }

    public Variable getVariable() {
        return variable;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return variable + "(" + arguments + ")";
    }
}