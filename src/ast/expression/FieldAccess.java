package ast.expression;

import visitor.Visitor;

public class FieldAccess extends AbstractExpression {

    private Expression expression;
    private String fieldName;

    public FieldAccess(int line, int column, Expression expression, String fieldName) {
        super(line, column);
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
    public String toString() {
        return expression + "." + fieldName;
    }


    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}