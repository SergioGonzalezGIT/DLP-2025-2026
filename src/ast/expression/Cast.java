package ast.expression;

import ast.type.Type;

public class Cast implements Expression {

    private int line;
    private int column;
    private Expression expression;
    private Type castType;

    public Cast(int line, int column, Expression expression, Type castType) {
        this.line = line;
        this.column = column;
        this.expression = expression;
        this.castType = castType;
    }

    public Expression getExpression() {
        return expression;
    }

    public Type getCastType() {
        return castType;
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
        return "(" + expression + " as " + castType + ")";
    }
}