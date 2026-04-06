package ast.expression;

import ast.Visitor;

public class ArrayAccess extends AbstractExpression {

    private int line;
    private int column;
    private Expression left;
    private Expression index;
    private boolean lvalue;

    public ArrayAccess(int line, int column, Expression left, Expression index) {
        this.line = line;
        this.column = column;
        this.left = left;
        this.index = index;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getIndex() {
        return index;
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
        return left + "[" + index + "]";
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