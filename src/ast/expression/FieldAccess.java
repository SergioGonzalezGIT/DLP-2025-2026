package ast.expression;

public class FieldAccess implements Expression {

    private int line;
    private int column;
    private Expression expression;
    private String fieldName;

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
}