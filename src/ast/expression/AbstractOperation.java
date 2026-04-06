package ast.expression;

public abstract class AbstractOperation extends AbstractExpression {

    private int line;
    private int column;
    private Expression left;
    private Expression right;
    private String operator;

    public AbstractOperation(int line, int column, Expression left, Expression right, String operator) {
        this.line = line;
        this.column = column;
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
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
        return left + " " + operator + " " + right;
    }
}