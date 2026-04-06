package ast.expression;

import ast.Visitor;
import ast.definition.Definition;

public class Variable extends AbstractExpression {

    private int line;
    private int column;
    private String name;
    private boolean lvalue;

    private Definition definition;

    public Variable(int line, int column, String name) {
        this.line = line;
        this.column = column;
        this.name = name;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public String getName() {
        return name;
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
        return name;
    }

    @Override
    public boolean getLValue() {
        return this.lvalue;
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