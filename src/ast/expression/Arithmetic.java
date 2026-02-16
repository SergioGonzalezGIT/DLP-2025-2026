package ast.expression;

public class Arithmetic extends AbstractOperation {
    public Arithmetic(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
    }
}