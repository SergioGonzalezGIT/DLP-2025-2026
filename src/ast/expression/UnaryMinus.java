package ast.expression;

public class UnaryMinus implements Expression {

    private int line;
    private int column;
    private Expression expression;

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
}