package ast.expression;

import ast.type.Type;

public abstract class AbstractExpression implements Expression {
    private boolean lvalue;
    private Type type;

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
