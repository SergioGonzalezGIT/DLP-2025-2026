package ast.statement;

import visitor.Visitor;
import ast.expression.Expression;

import java.util.List;

public class Input extends AbstractStatement {

    private List<Expression> expressions;

    public Input(int line, int column, List<Expression> expressions) {
        super(line, column);
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void addExpression(Expression exp) {
        this.expressions.add(exp);
    }

    @Override
    public String toString() {
        return "input " + expressions + ";";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}