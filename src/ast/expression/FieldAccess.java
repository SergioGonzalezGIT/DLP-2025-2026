package ast.expression;

import ast.Visitor;

public class FieldAccess extends AbstractExpression {

    private int line;
    private int column;
    private Expression expression;
    private String fieldName;
    private boolean lvalue;

    public FieldAccess(int line, int column, Expression expression, String fieldName) {
        this.line = line;
        this.column = column;
        this.expression = expression;
        this.fieldName = fieldName;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getFieldName() {
        return fieldName;
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
        return expression + "." + fieldName;
    }

    @Override
    public boolean getLValue() {
        return lvalue;
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