package ast.expression;

import ast.Visitor;

public class UnaryMinus extends AbstractExpression {

    private int line;
    private int column;
    private Expression expression;
    private boolean lvalue;

    public UnaryMinus(int line, int column, Expression expression) {
        this.line = line;
        this.column = column;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
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
        return "-" + expression;
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