package ast.expression;

import ast.Visitor;

import java.util.List;

public class FunctionInvocation extends AbstractExpression {

    private int line;
    private int column;
    private Variable variable;
    private List<Expression> arguments;
    private boolean lvalue;

    public FunctionInvocation(int line, int column, Variable variable, List<Expression> arguments) {
        this.line = line;
        this.column = column;
        this.variable = variable;
        this.arguments = arguments;
    }

    public void addArgument(Expression arg) {
        this.arguments.add(arg);
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

    @Override
    public boolean getLValue() {
        return false;
    }

    @Override
    public void setLValue(boolean lvalue) {
        this.lvalue = lvalue;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}