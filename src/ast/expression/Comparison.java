package ast.expression;

public class Comparison extends AbstractOperation {
    public Comparison(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
    }
}