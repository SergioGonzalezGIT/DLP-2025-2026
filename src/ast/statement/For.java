package ast.statement;

import ast.expression.Expression;
import visitor.Visitor;
import java.util.List;

public class For extends AbstractStatement {

    private Statement init;
    private Expression condition;
    private Statement update;
    private List<Statement> body;

    public For(int line, int column, Statement init, Expression condition, Statement update, List<Statement> body) {
        super(line, column);
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public Statement getInit() { return init; }
    public Expression getCondition() { return condition; }
    public Statement getUpdate() { return update; }
    public List<Statement> getBody() { return body; }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}