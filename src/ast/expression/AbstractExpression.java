package ast.expression;

import ast.type.Type;

public abstract class AbstractExpression implements Expression {
    private boolean lvalue;
    private Type type;
    private int line;
    private int column;

    public AbstractExpression(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public int getLine() { return line; }

    @Override
    public int getColumn() { return column; }

    @Override
    public boolean getLValue() {
        return this.lvalue;
    }

    @Override
    public void setLValue(boolean lvalue) {
        this.lvalue = lvalue;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
