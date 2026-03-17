package ast.expression;

import ast.Visitor;

public class Variable implements Expression {

    private int line;
    private int column;
    private String name;
    private boolean lvalue;

    public Variable(int line, int column, String name) {
        this.line = line;
        this.column = column;
        this.name = name;
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