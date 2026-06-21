package ast.statement;

import ast.expression.Expression;
import visitor.Visitor;
import java.util.List;

public class Case extends AbstractStatement {

    private Expression condition;
    private List<Statement> body;

    public Case(int line, int column, Expression condition, List<Statement> body) {
        super(line, column);
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "case " + condition + ": ...";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}