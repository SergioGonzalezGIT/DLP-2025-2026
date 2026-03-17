package ast.expression;

import ast.Visitor;

public class IntLiteral implements Expression {

    private int line;
    private int column;
    private int value;
    private boolean lvalue;

    public IntLiteral(int line, int column, int value) {
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public int getValue() {
        return value;
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
        return String.valueOf(value);
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