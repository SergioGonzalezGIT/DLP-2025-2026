package ast.statement;

import ast.expression.Expression;
import visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class Switch extends AbstractStatement {
    private Expression condition;
    private List<Case> cases;
    private List<Statement> defaultBody;

    public Switch(int line, int column, Expression condition, List<Case> cases, List<Statement> defaultBody) {
        super(line, column);
        this.condition = condition;
        this.cases = cases;
        this.defaultBody = defaultBody != null ? defaultBody : new ArrayList<>();
    }

    public Expression getCondition() { return condition; }
    public List<Case> getCases() { return cases; }
    public List<Statement> getDefaultBody() { return defaultBody; }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}