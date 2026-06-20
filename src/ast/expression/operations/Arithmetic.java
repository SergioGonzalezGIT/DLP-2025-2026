package ast.expression.operations;

import ast.expression.Expression;
import visitor.Visitor;

public class Arithmetic extends AbstractOperation {
    public Arithmetic(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}