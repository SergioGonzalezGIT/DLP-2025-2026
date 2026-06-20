package ast.definition;

import visitor.Visitor;
import ast.statement.Statement;
import ast.type.Type;

public class VarDefinition extends AbstractDefinition implements Statement {

    private int offset;

    public VarDefinition(int line, int column, String name, Type type) {
        super(line, column, name, type);
    }

    @Override
    public String toString() {
        return "let " + getName() + " : " + getType() + ";";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }
}