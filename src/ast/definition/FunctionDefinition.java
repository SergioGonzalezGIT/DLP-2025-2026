package ast.definition;

import visitor.Visitor;
import ast.statement.Statement;
import ast.type.Type;

import java.util.List;

public class FunctionDefinition extends AbstractDefinition {

    private List<Statement> statements;

    public FunctionDefinition(int line, int column, String name, Type type, List<Statement> statements) {
        super(line, column, name, type);
        this.statements = statements;
    }

    public void addStatement(Statement st) {
        this.statements.add(st);
    }

    public List<Statement> getStatements() { return statements; }

    @Override
    public String toString() {
        return "FunctionDefinition{" +
                "line=" + getLine() +
                ", column=" + getColumn() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                ", statements=" + statements +
                '}';
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}