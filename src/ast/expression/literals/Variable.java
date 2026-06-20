package ast.expression.literals;

import ast.expression.AbstractExpression;
import visitor.Visitor;
import ast.definition.Definition;

public class Variable extends AbstractExpression {

    private String name;

    private Definition definition;

    public Variable(int line, int column, String name) {
        super(line, column);
        this.name = name;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }


    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}