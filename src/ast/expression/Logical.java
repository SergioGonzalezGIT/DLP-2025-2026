package ast.expression;

public class Logical extends AbstractOperation {
    public Logical(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
    }
}