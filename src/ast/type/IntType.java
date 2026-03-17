package ast.type;

import ast.Visitor;

public class IntType implements  Type{

    private static IntType instance = new IntType();

    private IntType() {}

    public static IntType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP param) {
        return visitor.visit(this, param);
    }
}
