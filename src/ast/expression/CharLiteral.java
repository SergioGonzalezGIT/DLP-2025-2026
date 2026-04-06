package ast.expression;

import ast.Visitor;

public class CharLiteral extends AbstractExpression {

    private int line;
    private int column;
    private char value;
    private boolean lvalue;

    public CharLiteral(int line, int column, char value) {
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public char getValue() {
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
        return "'" + value + "'";
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