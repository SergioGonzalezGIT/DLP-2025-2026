package ast.statement;

import ast.expression.Expression;
import visitor.Visitor;

public class Incremento extends AbstractStatement {

    private Expression expression; // El 'i' de 'i++'

    public Incremento(int line, int column, Expression expression) {
        super(line, column);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}