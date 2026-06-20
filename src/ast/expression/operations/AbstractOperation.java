package ast.expression.operations;

import ast.expression.AbstractExpression;
import ast.expression.Expression;

public abstract class AbstractOperation extends AbstractExpression {

    private Expression left;
    private Expression right;
    private String operator;

    public AbstractOperation(int line, int column, Expression left, Expression right, String operator) {
        super(line, column);;
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
    public String toString() {
        return left + " " + operator + " " + right;
    }
}