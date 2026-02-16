package ast.statement;

import ast.expression.Expression;
import ast.expression.Variable;
import java.util.List;

public class Invocation implements Statement {

    private int line;
    private int column;
    private Variable variable;
    private List<Expression> arguments;

    public Invocation(int line, int column, Variable variable, List<Expression> arguments) {
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
        return variable + "(" + arguments + ");";
    }
}