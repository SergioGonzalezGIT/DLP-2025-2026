package ast.expression;

import ast.Visitor;

public class Arithmetic extends AbstractOperation {
    private boolean lvalue;
    public Arithmetic(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
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