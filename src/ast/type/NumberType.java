package ast.type;

import ast.Visitor;

public class NumberType implements Type {

    private static NumberType instance = new NumberType();

    private NumberType() {}

    public static NumberType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "number";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
